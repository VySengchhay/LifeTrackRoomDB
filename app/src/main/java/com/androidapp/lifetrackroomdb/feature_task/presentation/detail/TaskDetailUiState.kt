package com.androidapp.lifetrackroomdb.feature_task.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskModel
import com.androidapp.lifetrackroomdb.feature_task.domain.usecase.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class TaskDetailUiState(
    val task: TaskModel? = null,
    val isLoading: Boolean = true,
    val isDeleted: Boolean = false
)

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val taskUseCase: TaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskDetailUiState())
    val uiState: StateFlow<TaskDetailUiState> = _uiState

    fun loadTask(taskId: Int) {
        viewModelScope.launch {
            val task = taskUseCase.getTaskById(taskId)
            _uiState.value = TaskDetailUiState(
                task = task,
                isLoading = false
            )
        }
    }

    fun deleteTask() {
        val taskId = _uiState.value.task?.id ?: return

        viewModelScope.launch {
            taskUseCase.deleteTask(taskId)
            _uiState.value = _uiState.value.copy(isDeleted = true)
        }
    }
}