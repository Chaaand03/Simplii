package com.bytes.a.half.simplii_android

import com.bytes.a.half.simplii_android.APIConstants.BASE_URL
import com.bytes.a.half.simplii_android.models.Task
import com.bytes.a.half.simplii_android.utils.FirebaseUtils
import kotlinx.coroutines.tasks.await
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIHelper {

    fun getInstance(): Retrofit {
        val httpClientBuilder = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val request =
                chain.request().newBuilder().addHeader("Content-Type", "application/json").build()
            chain.proceed(request)
        })
        httpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        val httpClient = httpClientBuilder.build()
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(httpClient)
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }

    fun addTask(task: Task) {
        val userId = FirebaseUtils.auth.uid
        if (userId.isValidString()) {
            val tasksReference = FirebaseUtils.database.child("tasks").child(userId!!)
            val reference = tasksReference.push()
            task.id = reference.key
            val tasks: HashMap<String, Task> = HashMap()
            tasks[task.id!!] = task
            tasksReference.updateChildren(tasks as Map<String, Any>)
        }
    }

    fun updateTaskStatus(task: Task) {
        val userId = FirebaseUtils.auth.uid
        if (userId.isValidString()) {
            val tasksReference = FirebaseUtils.database.child("tasks").child(userId!!)
            val tasks: HashMap<String, Task> = HashMap()
            tasks[task.id!!] = task
            tasksReference.updateChildren(tasks as Map<String, Any>)
        }
    }


    fun deleteTask(task: Task) {
        val userId = FirebaseUtils.auth.uid
        if (userId.isValidString()) {
            val tasksReference = FirebaseUtils.database.child("tasks").child(userId!!)
            if (task.id.isValidString()) {
                tasksReference.child(task.id!!).removeValue()
            }
        }
    }

    suspend fun getTasks(): ArrayList<Task> {
        val tasksList = ArrayList<Task>()
        val userId = FirebaseUtils.auth.uid
        if (userId.isValidString()) {
            val tasksReference = FirebaseUtils.database.child("tasks").child(userId!!)
            val taskSnapshots = tasksReference.get().await()
            for (snapshot in taskSnapshots.children) {
                val task = snapshot.getValue(Task::class.java)
                if (task != null) {
                    tasksList.add(task)
                }
            }
        }
        return tasksList
    }


}