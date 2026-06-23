package com.androidapp.lifetrackroomdb.feature_task.domain.model

data class TaskModel(
    val id: Int = 0,
    val title: String,
    val description: String,
    val status: String,
    val priority: String,
    val createdAt: Long,
)
