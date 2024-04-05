package com.bytes.a.half.simplii_android

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bytes.a.half.simplii_android.composables.getContainerColor
import com.bytes.a.half.simplii_android.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class ContextTodo : AppCompatActivity() {

    private val todoItems: MutableList<Task> = mutableStateListOf()

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.context_todo),
                            fontSize = 20.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            finish()
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
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
                val response = remember {
                    mutableStateOf("")
                }
                var showProgress = remember {
                    mutableStateOf(false)
                }
                var showQueryTitle = remember {
                    mutableStateOf(false)
                }
                var contextQuery by remember {
                    mutableStateOf(TextFieldValue(""))
                }

                var showResponse = remember {
                    mutableStateOf(false)
                }

                val coroutineScope = rememberCoroutineScope()
                ConstraintLayout(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(it)
                ) {

                    val (todoList, progress, container, query, addToList) = createRefs()
                    LazyColumn(modifier = Modifier.constrainAs(container) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }) {
                        item {
                            if (!showQueryTitle.value) {
                                OutlinedTextField(modifier = Modifier
                                    .heightIn(50.dp)
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                    value = contextQuery,
                                    onValueChange = { text ->
                                        contextQuery = text
                                    },
                                    keyboardActions = KeyboardActions(onDone = {
                                        showProgress.value = true
                                        showQueryTitle.value = true
                                        coroutineScope.launch(Dispatchers.IO) {
                                            val chatGPTResponse = SimpliiUtils.queryChatGPT(
                                                this@ContextTodo,
                                                contextQuery.text.trimEnd()
                                            )
                                            coroutineScope.launch(Dispatchers.Main) {
                                                todoItems.clear()
                                                todoItems.addAll(chatGPTResponse)
                                                showResponse.value = true
                                                showProgress.value = false
                                            }
                                        }
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
                                            text = stringResource(id = R.string.how_can_i_help),
                                            color = Color.Black.copy(alpha = ContentAlpha.medium)
                                        )
                                    })
                            }
                        }

                        item {
                            if (!showQueryTitle.value) {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    Button(
                                        onClick = {
                                            showProgress.value = true
                                            showQueryTitle.value = true
                                            coroutineScope.launch(Dispatchers.IO) {
                                                val chatGPTResponse = SimpliiUtils.queryChatGPT(
                                                    this@ContextTodo,
                                                    contextQuery.text.trimEnd()
                                                )
                                                coroutineScope.launch(Dispatchers.Main) {
                                                    todoItems.clear()
                                                    todoItems.addAll(chatGPTResponse)
                                                    showResponse.value = true
                                                    showProgress.value = false
                                                }
                                            }
                                        },
                                        modifier = Modifier.align(Alignment.Center),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Blue,
                                            contentColor = Color.White
                                        )
                                    ) {
                                        Text(stringResource(id = R.string.ask_tudum))
                                    }
                                }


                            }
                        }

                        item {
                            Text(response.value, modifier = Modifier.padding(16.dp))
                        }
                    }

                    if (showQueryTitle.value) {
                        Text(
                            text = contextQuery.text,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(16.dp)
                                .constrainAs(query) {
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    top.linkTo(parent.top)
                                }
                        )
                    }


                    if (showProgress.value) {
                        Box(modifier = Modifier
                            .constrainAs(progress) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }

                    if (showResponse.value) {
                        LazyColumn(modifier = Modifier
                            .constrainAs(todoList) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(query.bottom)
                                bottom.linkTo(addToList.top)
                                height = Dimension.fillToConstraints

                            }) {
                            itemsIndexed(todoItems) { index, item ->
                                Card(
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = getContainerColor(
                                            item.status
                                        )
                                    )
                                ) {
                                    ListItem(text = {
                                        Text(item.title ?: "")
                                    })
                                }

                            }
                        }

                        Button(onClick = {
                            onAddTasksToList(todoItems)
                        }, modifier = Modifier
                            .constrainAs(addToList) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                                width = Dimension.fillToConstraints
                            }
                            .padding(16.dp), colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue,
                            contentColor = Color.White
                        )) {
                            Text(text = stringResource(id = R.string.add_to_list))
                        }
                    }
                }
            }


        }
    }

    private fun onAddTasksToList(todoItems: List<Task>) {
        todoItems.forEach {
            APIHelper.addTask(it)
        }
        val intent = Intent()
        intent.putExtra(SimpliiConstants.KEY_REFRESH_LIST,true)
        setResult(Activity.RESULT_OK)
        finish()
    }
}