package com.bytes.a.half.simplii_android.models

import java.util.Date

data class Reminder(
    var id: String? = "",
    var taskId: String? = "",
    var userId: String? = "",
    var taskTitle : String? = "",
    var reminderDate: Date = Date()
) {
}