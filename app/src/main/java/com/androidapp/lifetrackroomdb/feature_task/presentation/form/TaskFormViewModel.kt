package com.androidapp.lifetrackroomdb.feature_task.presentation.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskModel
import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskPriority
import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskStatus
import com.androidapp.lifetrackroomdb.feature_task.domain.usecase.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaskFormUiState(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val status: String = TaskStatus.TODO.name,
    val priority: String = TaskPriority.MEDIUM.name,
    val createdAt: Long = System.currentTimeMillis(),
    val isSaved: Boolean = false,
)

@HiltViewModel
class TaskFormViewModel @Inject constructor(
    private val taskUseCase: TaskUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskFormUiState())
    val uiState: StateFlow<TaskFormUiState> = _uiState

    fun loadTask(taskId: Int?) {
        if (taskId == null) return

        viewModelScope.launch {
            val task = taskUseCase.getTaskById(taskId) ?: return@launch
            _uiState.value = TaskFormUiState(
                id = task.id,
                title = task.title,
                description = task.description,
                status = task.status,
                priority = task.priority,
                createdAt = task.createdAt
            )
        }
    }

    fun onTitleChange(value: String) {
        _uiState.value = _uiState.value.copy(title = value)
    }

    fun onDescriptionChange(value: String) {
        _uiState.value = _uiState.value.copy(description = value)
    }

    fun onStatusChange(value: String) {
        _uiState.value = _uiState.value.copy(status = value)
    }

    fun onPriorityChange(value: String) {
        _uiState.value = _uiState.value.copy(priority = value)
    }

    fun saveTask() {
        val state = _uiState.value

        if (state.title.isBlank()) return

        viewModelScope.launch {
            val task = TaskModel(
                id = state.id ?: 0,
                title = state.title.trim(),
                description = state.description.trim(),
                status = state.status,
                priority = state.priority,
                createdAt = state.createdAt
            )

            if (state.id == null) {
                taskUseCase.createTask(task)
            } else {
                taskUseCase.updateTask(task)
            }

            _uiState.value = state.copy(isSaved = true)
        }
    }
}