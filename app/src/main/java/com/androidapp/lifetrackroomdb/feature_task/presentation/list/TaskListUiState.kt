package com.androidapp.lifetrackroomdb.feature_task.presentation.list

import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskModel

data class TaskListUiState(
    val tasks: List<TaskModel> = emptyList(),
    val search: String? = null,
    val status: String? = null,
    val priority: String? = null,
    val isLoading: Boolean = false,
)