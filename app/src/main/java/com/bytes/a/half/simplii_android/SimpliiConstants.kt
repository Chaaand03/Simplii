package com.bytes.a.half.simplii_android

object SimpliiConstants {


    const val DUE_DATE_PICKER = 0
    const val START_DATE_PICKER = 1


    const val KEY_TASK_TITLE = "taskTitle"
    const val KEY_TASK_START_DATE = "taskStartDate"
    const val KEY_TASK_END_DATE = "taskEndDate"
    const val KEY_TASK_HOURS = "taskHours"
    const val KEY_TASK_STATUS = "taskStatus"
    const val KEY_TASK_CATEGORY = "taskCategory"
    const val KEY_TASK_ID = "taskId"
    const val KEY_REFRESH_LIST = "refreshList"

    const val CREATE_TASK_REQUEST_CODE = 1
    const val SET_REMINDER_REQUEST_CODE = 2
    const val CONTEXT_TODO_REQUEST_CODE = 3
    const val SHOW_REMINDERS = 4



    const val TASK_STATUS_TODO = 1
    const val TASK_STATUS_IN_PROGRESS = 2
    const val TASK_STATUS_DONE = 3



    object TaskCategory {
        const val INTELLECTUAL = 1
        const val PHYSICAL = 2
        const val SMART_TODO = 3
    }

    object TaskStatus {
        const val TODO = 1
        const val IN_PROGRESS = 2
        const val DONE = 3
    }



}