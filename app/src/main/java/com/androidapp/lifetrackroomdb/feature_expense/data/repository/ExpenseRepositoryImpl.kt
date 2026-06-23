package com.androidapp.lifetrackroomdb.feature_expense.data.repository

import com.androidapp.lifetrackroomdb.feature_expense.data.local.ExpenseDao
import com.androidapp.lifetrackroomdb.feature_expense.data.mapper.toEntity
import com.androidapp.lifetrackroomdb.feature_expense.data.mapper.toModel
import com.androidapp.lifetrackroomdb.feature_expense.domain.model.ExpenseModel
import com.androidapp.lifetrackroomdb.feature_expense.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {
    override suspend fun createExpense(expense: ExpenseModel) {
        return expenseDao.createExpense(expense.toEntity())
    }

    override fun getExpenses(): Flow<List<ExpenseModel>> {
        return expenseDao.getExpenses().map { list ->
            list.map { it.toModel() }
        }
    }

    override suspend fun getExpenseById(expenseId: Int): ExpenseModel {
        return expenseDao.getExpenseById(expenseId).toModel()
    }

    override suspend fun updateExpense(expense: ExpenseModel) {
        expenseDao.updateExpense(expense.toEntity())
    }

    override suspend fun deleteExpense(expenseId: Int) {
        expenseDao.deleteExpense(expenseId)
    }

    override fun filterExpenses(
        search: String?,
        category: String?,
        minAmount: Double?,
        maxAmount: Double?
    ): Flow<List<ExpenseModel>> {
        return expenseDao.filterExpenses(
            search = search,
            category = category,
            minAmount = minAmount,
            maxAmount = maxAmount
        ).map { list ->
            list.map { it.toModel() }
        }
    }
}