package com.bytes.a.half.simplii_android

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.RetryStrategy
import com.bytes.a.half.simplii_android.models.Reminder
import com.bytes.a.half.simplii_android.models.Task
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.time.Duration.Companion.seconds

object SimpliiUtils {

    fun scheduleReminder(context: Context, reminder: Reminder) {
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val toIntent = Intent(context, AlarmReceiver::class.java)
        toIntent.putExtra(SimpliiConstants.KEY_TASK_TITLE, reminder.taskTitle)


        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(
                context,
                SimpliiConstants.SET_REMINDER_REQUEST_CODE, toIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getBroadcast(
                context,
                SimpliiConstants.SET_REMINDER_REQUEST_CODE, toIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> alarmMgr.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                reminder.reminderDate.time,
                pendingIntent
            )

            else -> alarmMgr.set(AlarmManager.RTC_WAKEUP, reminder.reminderDate.time, pendingIntent)
        }
    }

    fun formatDate(date: Date): String {
        val dateFormatter = SimpleDateFormat("MM/dd/yyyy hh:mm")
        return dateFormatter.format(date)
    }

    fun formatSelectedDate(date: Date) : String {
        val dateFormatter = SimpleDateFormat("MM/dd/yyyy")
        return dateFormatter.format(date)
    }

    fun formatSelectedTime(date: Date) : String {
        val dateFormatter = SimpleDateFormat("hh:mm a")
        return dateFormatter.format(date)
    }

    suspend fun queryChatGPT(context: Context, query: String): List<Task> {
        val todoList = ArrayList<Task>()
        val openAI = OpenAI(
            token = context.getString(R.string.open_ai_api_key),
            retry = RetryStrategy(maxRetries = 1)
            // additional configurations...
        )

        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo"),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.User,
                    content = "${query}\n Give the output in HTML format."
                )
            )
        )
        val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
        val response = completion.choices.first().message.content ?: ""
        val document = Jsoup.parse(response)
        document.outputSettings().prettyPrint(false)

        val listItems = document.select("li")

        listItems.forEach {
            val task = Task()
            task.title = it.text()
            task.startDate = Date()
            task.category = SimpliiConstants.TaskCategory.SMART_TODO
            task.status = SimpliiConstants.TaskStatus.TODO
            task.hours = 1
            todoList.add(task)
        }

        return todoList

    }
}