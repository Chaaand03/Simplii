package com.bytes.a.half.simplii_android

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.lifecycleScope
import com.bytes.a.half.simplii_android.SimpliiConstants.TASK_STATUS_IN_PROGRESS
import com.bytes.a.half.simplii_android.SimpliiConstants.TASK_STATUS_TODO
import com.bytes.a.half.simplii_android.composables.HomeScreen
import com.bytes.a.half.simplii_android.models.Task
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : AppCompatActivity() {
    private val todoItems: MutableList<Task> = mutableStateListOf()
    private val inProgressItems: MutableList<Task> = mutableStateListOf()
    private val doneItems: MutableList<Task> = mutableStateListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LaunchedEffect(Unit) {
                fetchTasks()
            }
            HomeScreen(todoItems, inProgressItems, doneItems, onSetReminder = { task ->
                setReminder(task)
            }, onShowReminders = {
                showReminders()
            }, onContextTodos = {
                openContextTodos()
            }) {
                openCreateTask()
            }
        }
    }

    private fun openCreateTask() {
        val intent = Intent(this, CreateTaskActivity::class.java)
        startActivityForResult(intent, SimpliiConstants.CREATE_TASK_REQUEST_CODE)
    }


    private fun setReminder(task: Task) {
        val intent = Intent(this, ReminderActivity::class.java)
        intent.putExtra(SimpliiConstants.KEY_TASK_TITLE, task.title)
        intent.putExtra(SimpliiConstants.KEY_TASK_ID, task.id)
        startActivityForResult(intent, SimpliiConstants.SET_REMINDER_REQUEST_CODE)
    }


    private fun showReminders() {
        val intent = Intent(this, ReminderListActivity::class.java)
        startActivityForResult(intent, SimpliiConstants.SHOW_REMINDERS)

    }

    private fun openContextTodos() {
        val intent = Intent(this, ContextTodo::class.java)
        startActivityForResult(intent, SimpliiConstants.CONTEXT_TODO_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SimpliiConstants.CREATE_TASK_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val newTask = Task(
                        title = data.getStringExtra(SimpliiConstants.KEY_TASK_TITLE),
                        status = data.getIntExtra(SimpliiConstants.KEY_TASK_STATUS, -1),
                        hours = data.getIntExtra(SimpliiConstants.KEY_TASK_HOURS, -1),
                        startDate = Date(
                            data.getLongExtra(
                                SimpliiConstants.KEY_TASK_START_DATE,
                                -1L
                            )
                        ),
                        dueDate = Date(
                            data.getLongExtra(
                                SimpliiConstants.KEY_TASK_END_DATE,
                                -1L
                            )
                        ), category = data.getIntExtra(SimpliiConstants.KEY_TASK_CATEGORY, -1)
                    )

                    APIHelper.addTask(newTask)

                    when (newTask.status) {
                        TASK_STATUS_TODO -> {
                            todoItems.add(newTask)
                        }

                        TASK_STATUS_IN_PROGRESS -> {
                            inProgressItems.add(newTask)
                        }

                        else -> {
                            doneItems.add(newTask)
                        }
                    }

                }
            }

            SimpliiConstants.SET_REMINDER_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {

                }
            }

            SimpliiConstants.CONTEXT_TODO_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    lifecycleScope.launch {
                        fetchTasks()
                    }
                }
            }

        }
    }

    suspend fun fetchTasks() {
        val tasks = APIHelper.getTasks()
        todoItems.clear()
        inProgressItems.clear()
        doneItems.clear()
        tasks.forEach {
            when (it.status) {
                SimpliiConstants.TaskStatus.TODO -> {
                    todoItems.add(it)
                }

                SimpliiConstants.TaskStatus.IN_PROGRESS -> {
                    inProgressItems.add(it)
                }

                else -> {
                    doneItems.add(it)
                }
            }
        }
    }
}