package com.bytes.a.half.simplii_android.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bytes.a.half.simplii_android.R
import com.bytes.a.half.simplii_android.SimpliiConstants
import com.bytes.a.half.simplii_android.SimpliiConstants.DUE_DATE_PICKER
import com.bytes.a.half.simplii_android.SimpliiConstants.START_DATE_PICKER
import com.bytes.a.half.simplii_android.SimpliiUtils.formatSelectedDate
import com.bytes.a.half.simplii_android.models.Task
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(onClose: () -> Unit, onCreateTask: (task: Task) -> Unit) {

    var taskTitle by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var hoursText by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var datePickerOption by remember {
        mutableIntStateOf(DUE_DATE_PICKER)
    }
    var startDateString by remember {
        mutableStateOf("")
    }
    var dueDateString by remember {
        mutableStateOf("")
    }
    var startDate by remember {
        mutableLongStateOf(-1L)
    }
    var endDate by remember {
        mutableLongStateOf(-1L)
    }
    var category by remember {
        mutableIntStateOf(SimpliiConstants.TaskCategory.INTELLECTUAL)
    }
    var status by remember {
        mutableIntStateOf(SimpliiConstants.TaskStatus.TODO)
    }
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.create_task),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    onClose()
                }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            actions = {
                IconButton(onClick = {
                    val newTask = Task()
                    newTask.title = taskTitle.text
                    newTask.startDate = Date(startDate)
                    newTask.dueDate = Date(endDate)
                    newTask.hours = hoursText.text.toInt()
                    newTask.category = category
                    newTask.status = status
                    onCreateTask(newTask)
                }) {
                    Icon(Icons.Filled.Done, contentDescription = null)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Blue,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            )
        )
    }) {

        val openDatePickerDialog = remember { mutableStateOf(false) }
        var pickerValue by remember { mutableIntStateOf(0) }
        rememberDatePickerState(initialSelectedDateMillis = Date().time)
        rememberDatePickerState(initialSelectedDateMillis = Date().time)
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                OutlinedTextField(modifier = Modifier
                    .heightIn(50.dp)
                    .fillMaxWidth()
                    .padding(16.dp),
                    value = taskTitle,
                    onValueChange = { text ->
                        taskTitle = text
                    },
                    keyboardActions = KeyboardActions(onDone = {

                    }),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        cursorColor = Color.Black
                    ),
                    label = {
                        Text(
                            text = stringResource(id = R.string.title),
                            color = Color.Black.copy(alpha = ContentAlpha.medium)
                        )
                    })
            }

            item {
                Divider()
            }

            item {
                val statusOptions = listOf(
                    stringResource(id = R.string.todo),
                    stringResource(id = R.string.in_progress),
                    stringResource(id = R.string.done)
                )
                val (selectedOption, onOptionSelected) = remember { mutableStateOf(statusOptions[0]) }
                Column(Modifier.selectableGroup()) {
                    Text(
                        text = stringResource(id = R.string.status),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                    statusOptions.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = {
                                        when (text) {
                                            "Todo" -> status = SimpliiConstants.TaskStatus.TODO
                                            "In progress" -> status =
                                                SimpliiConstants.TaskStatus.IN_PROGRESS

                                            else -> status = SimpliiConstants.TaskStatus.DONE
                                        }
                                        onOptionSelected(text)
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null // null recommended for accessibility with screenreaders
                            )
                            Text(
                                text = text, modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }

                }
            }
            item {
                Divider()
            }

            item {
                val categoryOptions = listOf(
                    stringResource(id = R.string.intellectual),
                    stringResource(id = R.string.physical)
                )
                val (selectedOption, onOptionSelected) = remember { mutableStateOf(categoryOptions[0]) }
                Column(Modifier.selectableGroup()) {
                    Text(
                        text = stringResource(id = R.string.category),
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp
                    )
                    categoryOptions.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = {
                                        when (text) {
                                            "Intellectual" -> {
                                                category =
                                                    SimpliiConstants.TaskCategory.INTELLECTUAL
                                            }

                                            else -> {
                                                category = SimpliiConstants.TaskCategory.PHYSICAL
                                            }
                                        }
                                        onOptionSelected(text)
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null // null recommended for accessibility with screenreaders
                            )
                            Text(
                                text = text, modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }

                }
            }
            item {
                Divider()
            }


            // category
            // status

            item {
                OutlinedTextField(modifier = Modifier
                    .heightIn(50.dp)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    value = hoursText,
                    onValueChange = { text ->
                        hoursText = text
                    },
                    keyboardActions = KeyboardActions(onDone = {

                    }),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        cursorColor = Color.Black
                    ),
                    label = {
                        Text(
                            text = stringResource(id = R.string.hours),
                            color = Color.Black.copy(alpha = ContentAlpha.medium)
                        )
                    })
            }// hours

            item {
                if (openDatePickerDialog.value) {
                    val datePickerState = rememberDatePickerState()
                    DatePickerDialog(onDismissRequest = {
                        openDatePickerDialog.value = false
                    }, confirmButton = {
                        TextButton(
                            onClick = {
                                openDatePickerDialog.value = false
                                val calendar = Calendar.getInstance()
                                calendar.timeInMillis = datePickerState.selectedDateMillis!!
                                calendar.add(Calendar.DATE, 1)
                                if (datePickerOption == DUE_DATE_PICKER) {
                                    dueDateString =
                                        formatSelectedDate(Date(calendar.timeInMillis))
                                    endDate = calendar.timeInMillis
                                } else {
                                    startDateString =
                                        formatSelectedDate(Date(calendar.timeInMillis))
                                    startDate = calendar.timeInMillis
                                }
                            }, enabled = true
                        ) {
                            Text("OK")
                        }
                    }, dismissButton = {
                        TextButton(onClick = {
                            openDatePickerDialog.value = false
                        }) {
                            Text("Cancel")
                        }
                    }) {
                        DatePicker(state = datePickerState)
                    }
                }
            }

            item {
                Column(modifier = Modifier.clickable {
                    datePickerOption = START_DATE_PICKER
                    openDatePickerDialog.value = true
                }) {
                    OutlinedTextField(value = startDateString, modifier = Modifier
                        .heightIn(50.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        enabled = false,
                        onValueChange = {

                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            cursorColor = Color.Black,
                            disabledTextColor = Color.Black,
                            disabledTrailingIconColor = Color.Black
                        ),
                        label = {
                            Text(
                                text = stringResource(id = R.string.start_date),
                                color = Color.Black.copy(alpha = ContentAlpha.medium)
                            )
                        },
                        trailingIcon = {
                            Icon(Icons.Filled.DateRange, "")
                        })
                }

            }

            item {
                Column(modifier = Modifier.clickable {
                    datePickerOption = DUE_DATE_PICKER
                    openDatePickerDialog.value = true
                }) {
                    OutlinedTextField(modifier = Modifier
                        .heightIn(50.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        value = dueDateString,
                        enabled = false,
                        onValueChange = {},
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            cursorColor = Color.Black,
                            disabledTextColor = Color.Black,
                            disabledTrailingIconColor = Color.Black
                        ),
                        label = {
                            Text(
                                text = stringResource(id = R.string.due_date),
                                color = Color.Black.copy(alpha = ContentAlpha.medium)
                            )
                        },
                        trailingIcon = {
                            Icon(Icons.Filled.DateRange, "")
                        })
                }
            }//due date


        }
    }
}