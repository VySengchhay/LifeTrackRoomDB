package com.androidapp.lifetrackroomdb.feature_expense.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidapp.lifetrackroomdb.core.database.TableName

@Entity(tableName = TableName.EXPENSE)
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val amount: Double,
    val category: String,
    val note: String,
    val createdAt: Long,
)
