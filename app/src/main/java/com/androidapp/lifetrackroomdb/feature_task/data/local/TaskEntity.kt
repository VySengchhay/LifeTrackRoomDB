package com.androidapp.lifetrackroomdb.feature_task.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidapp.lifetrackroomdb.core.database.TableName

@Entity(tableName = TableName.TASK)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val status: String,
    val priority: String,
    val createdAt: Long,
)
