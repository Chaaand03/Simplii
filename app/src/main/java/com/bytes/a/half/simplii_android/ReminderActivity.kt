package com.bytes.a.half.simplii_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.bytes.a.half.simplii_android.composables.ReminderScreen

class ReminderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val taskTitle = intent.getStringExtra(SimpliiConstants.KEY_TASK_TITLE) ?: ""
        val taskId = intent.getStringExtra(SimpliiConstants.KEY_TASK_ID) ?: ""
        setContent {
            ReminderScreen(taskId = taskId, taskTitle = taskTitle, onClose = { finish() }) { reminder ->
                APIHelper.addReminder(reminder)
                SimpliiUtils.scheduleReminder(this,reminder)
                finish()
            }
        }
    }
}