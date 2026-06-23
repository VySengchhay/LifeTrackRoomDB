package com.androidapp.lifetrackroomdb.feature_task.domain.usecase

import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskModel
import com.androidapp.lifetrackroomdb.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class TaskUseCase @Inject constructor(
    val createTask: CreateTaskUseCase,
    val getTasks: GetTasksUseCase,
    val getTaskById: GetTaskByIdUseCase,
    val updateTask: UpdateTaskUseCase,
    val deleteTask: DeleteTaskUseCase,
    val filterTasks: FilterTasksUseCase,
)

class CreateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(task: TaskModel) {
        taskRepository.createTask(task)
    }
}

class GetTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(): Flow<List<TaskModel>> {
        return taskRepository.getTasks()
    }
}

class GetTaskByIdUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(taskId: Int): TaskModel {
        return taskRepository.getTaskById(taskId)
    }
}

class UpdateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(task: TaskModel) {
        taskRepository.updateTask(task)
    }
}

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(taskId: Int) {
        taskRepository.deleteTask(taskId)
    }
}

class FilterTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(
        search: String?,
        status: String?,
        priority: String?,
    ): Flow<List<TaskModel>> {
        return taskRepository.filterTasks(
            search,
            status,
            priority
        )
    }
}