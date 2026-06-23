package com.androidapp.lifetrackroomdb.feature_task.data.mapper

import com.androidapp.lifetrackroomdb.feature_task.data.local.TaskEntity
import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskModel

fun TaskEntity.toModel() : TaskModel {
    return TaskModel(
        id = id,
        title = title,
        description = description,
        status = status,
        priority = priority,
        createdAt = createdAt,
    )
}

fun TaskModel.toEntity() : TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        status = status,
        priority = priority,
        createdAt = createdAt,
    )
}