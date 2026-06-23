package com.androidapp.lifetrackroomdb.feature_task.data.repository

import com.androidapp.lifetrackroomdb.feature_task.data.local.TaskDao
import com.androidapp.lifetrackroomdb.feature_task.data.local.TaskEntity
import com.androidapp.lifetrackroomdb.feature_task.data.mapper.toEntity
import com.androidapp.lifetrackroomdb.feature_task.data.mapper.toModel
import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskModel
import com.androidapp.lifetrackroomdb.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override suspend fun createTask(task: TaskModel) {
        taskDao.createTask(task.toEntity())
    }

    override fun getTasks(): Flow<List<TaskModel>> {
        return taskDao.getTasks().map { list ->
            list.map { it.toModel() }
        }
    }

    override suspend fun getTaskById(taskId: Int): TaskModel {
        return taskDao.getTaskById(taskId).toModel()
    }

    override suspend fun updateTask(task: TaskModel) {
        taskDao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

    override fun filterTasks(
        search: String?,
        status: String?,
        priority: String?
    ): Flow<List<TaskModel>> {
        return taskDao.filterTasks(
            search = search,
            status = status,
            priority = priority
        ).map { list ->
            list.map { it.toModel() }
        }
    }
}