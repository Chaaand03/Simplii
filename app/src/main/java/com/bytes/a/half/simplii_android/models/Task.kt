package com.bytes.a.half.simplii_android.models

import java.util.Date

data class Task(
    var id: String? = "",
    var title: String? = "",
    var status: Int = -1,
    var category : Int = -1,
    var hours: Int = -1,
    var startDate: Date = Date(),
    var dueDate: Date = Date()
)
