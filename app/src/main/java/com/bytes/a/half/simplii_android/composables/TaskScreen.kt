package com.bytes.a.half.simplii_android.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.bytes.a.half.simplii_android.R
import com.bytes.a.half.simplii_android.SimpliiConstants
import com.bytes.a.half.simplii_android.models.Task

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskScreen(tasks: MutableList<Task>, onSetReminder: (task: Task) -> Unit) {

    Scaffold {
        if (tasks.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            LazyColumn(modifier = Modifier.padding(it)) {
                itemsIndexed(tasks) { index, item ->
                    Card(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = getContainerColor(item.status))
                    ) {
                        ListItem(text = {
                            Text(item.title ?: "")
                        }, trailing = {
                            Icon(Icons.Filled.AccessAlarm, "", modifier = Modifier.clickable {
                                onSetReminder(item)
                            })
                        })
                    }

                }
            }
        }

    }
}


@Composable
fun getContainerColor(status: Int): Color {
    return when (status) {
        SimpliiConstants.TaskStatus.TODO -> {
            colorResource(id = R.color.todo_background)
        }

        SimpliiConstants.TaskStatus.IN_PROGRESS -> {
            colorResource(id = R.color.inprogress_background)
        }

        else -> {
            colorResource(id = R.color.done_background)
        }
    }
}