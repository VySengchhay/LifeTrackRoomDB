package com.androidapp.lifetrackroomdb.feature_task.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidapp.lifetrackroomdb.feature_task.domain.usecase.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskUseCase: TaskUseCase
) : ViewModel() {
    private val filterState = MutableStateFlow(TaskListUiState(isLoading = true))

    val uiState: StateFlow<TaskListUiState> = filterState
        .flatMapLatest { filter ->
            taskUseCase.filterTasks(
                search = filter.search?.ifBlank { null },
                status = filter.status,
                priority = filter.priority,
            ).map { tasks ->
                filter.copy(
                    tasks = tasks,
                    isLoading = false
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TaskListUiState(isLoading = true)
        )

    fun onSearchChange(value: String) {
        filterState.value = filterState.value.copy(search = value)
    }

    fun onStatusChange(value: String?) {
        filterState.value = filterState.value.copy(status = value)
    }

    fun onPriorityChange(value: String?) {
        filterState.value = filterState.value.copy(priority = value)
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            taskUseCase.deleteTask(taskId)
        }
    }
}