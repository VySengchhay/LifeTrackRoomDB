package com.androidapp.lifetrackroomdb.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidapp.lifetrackroomdb.feature_expense.data.local.ExpenseDao
import com.androidapp.lifetrackroomdb.feature_expense.data.local.ExpenseEntity
import com.androidapp.lifetrackroomdb.feature_task.data.local.TaskDao
import com.androidapp.lifetrackroomdb.feature_task.data.local.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
        ExpenseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LifeTrackDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun expenseDao(): ExpenseDao
}