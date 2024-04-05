package com.bytes.a.half.simplii_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import com.bytes.a.half.simplii_android.composables.ReminderListScreen
import com.bytes.a.half.simplii_android.models.Reminder

class ReminderListActivity : AppCompatActivity() {

    private val reminderList: MutableList<Reminder> = mutableStateListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect(Unit) {
                val reminders = APIHelper.getReminders()
                reminderList.clear()
                reminderList.addAll(reminders)
            }
            ReminderListScreen(reminders = reminderList) {
                finish()
            }
        }
    }
}