package com.bytes.a.half.simplii_android.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bytes.a.half.simplii_android.R
import com.bytes.a.half.simplii_android.SimpliiUtils.formatDate
import com.bytes.a.half.simplii_android.models.Reminder

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReminderListScreen(reminders: List<Reminder>, onClose: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.reminders)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Blue,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            ), navigationIcon = {
                IconButton(onClick = {
                    onClose()
                }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
    }) {
        LazyColumn(modifier = Modifier.padding(it)) {
            itemsIndexed(reminders) { index, item ->
                Card(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    ListItem(text = {
                        Text(item.taskTitle ?: "")
                    }, trailing = {
                        Text(text = formatDate(item.reminderDate))
                    })
                }

            }
        }
    }
}