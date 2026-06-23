package com.androidapp.lifetrackroomdb.feature_expense.domain.repository

import com.androidapp.lifetrackroomdb.feature_expense.domain.model.ExpenseModel
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun createExpense(expense: ExpenseModel)
    fun getExpenses(): Flow<List<ExpenseModel>>
    suspend fun getExpenseById(expenseId: Int): ExpenseModel
    suspend fun updateExpense(expense: ExpenseModel)
    suspend fun deleteExpense(expenseId: Int)
    fun filterExpenses(
        search: String?,
        category: String?,
        minAmount: Double?,
        maxAmount: Double?
    ): Flow<List<ExpenseModel>>
}