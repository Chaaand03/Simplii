package com.bytes.a.half.simplii_android.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.HotelClass
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.bytes.a.half.simplii_android.R
import com.bytes.a.half.simplii_android.models.Task
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    todoItems: MutableList<Task>,
    inProgressItems: MutableList<Task>,
    doneItems: MutableList<Task>,
    onSetReminder: (task: Task) -> Unit,
    onShowReminders: () -> Unit,
    onContextTodos : () -> Unit,
    onCreateNewTask: () -> Unit
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.simplii)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Blue,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            ), actions = {
                IconButton(onClick = {
                    onShowReminders()
                }) {
                    Icon(Icons.Filled.Notifications, contentDescription = null)
                }

                IconButton(onClick = {
                    onContextTodos()
                }) {
                    Icon(Icons.Filled.HotelClass, contentDescription = null)
                }
            }
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            onCreateNewTask()
        }, containerColor = Color.Blue) {
            Icon(
                Icons.Filled.Add, "",
                // If tint color is provided, it will override contentColor below
                tint = Color.White,
            )
        }
    }) {
        Column(modifier = Modifier.padding(it)) {
            val tabList = listOf(
                stringResource(id = R.string.todo),
                stringResource(id = R.string.in_progress),
                stringResource(id = R.string.done)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                TabRow(
                    selectedTabIndex = tabIndex,
                    containerColor = Color.White,
                    contentColor = Color.Blue,
                    indicator = @Composable { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[tabIndex]), color = Color.Blue
                        )
                    }
                ) {
                    tabList.forEachIndexed { index, title ->
                        Tab(text = { Text(title) },
                            selected = tabIndex == index,
                            onClick = { tabIndex = index }
                        )
                    }
                }
                when (tabIndex) {
                    0 -> TaskScreen(todoItems) { task ->
                        onSetReminder(task)
                    }

                    1 -> TaskScreen(inProgressItems) { task ->
                        onSetReminder(task)
                    }

                    2 -> TaskScreen(doneItems) { task ->
                        onSetReminder(task)
                    }
                }
            }
        }
    }
}