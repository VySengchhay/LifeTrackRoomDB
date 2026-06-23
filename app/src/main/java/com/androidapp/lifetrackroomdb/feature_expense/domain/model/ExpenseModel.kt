package com.androidapp.lifetrackroomdb.feature_expense.domain.model

data class ExpenseModel(
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val category: String,
    val note: String,
    val createdAt: Long,
)
