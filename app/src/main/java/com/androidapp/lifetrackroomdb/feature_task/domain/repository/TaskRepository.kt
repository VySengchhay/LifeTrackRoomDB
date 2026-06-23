package com.androidapp.lifetrackroomdb.feature_task.domain.repository

import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun createTask(task: TaskModel)
    fun getTasks(): Flow<List<TaskModel>>
    suspend fun getTaskById(taskId: Int): TaskModel
    suspend fun updateTask(task: TaskModel)
    suspend fun deleteTask(taskId: Int)
    fun filterTasks(
        search: String?,
        status: String?,
        priority: String?
    ): Flow<List<TaskModel>>
}