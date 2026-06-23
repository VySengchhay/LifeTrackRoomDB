package com.androidapp.lifetrackroomdb.feature_expense.domain.usecase

import com.androidapp.lifetrackroomdb.feature_expense.domain.model.ExpenseModel
import com.androidapp.lifetrackroomdb.feature_expense.domain.repository.ExpenseRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

data class ExpenseUseCase @Inject constructor(
    val createExpense: CreateExpenseUseCase,
    val getExpenses: GetExpensesUseCase,
    val getExpenseById: GetExpenseByIdUseCase,
    val updateExpense: UpdateExpenseUseCase,
    val deleteExpense: DeleteExpenseUseCase,
    val filterExpenses: FilterExpensesUseCase,
)

class CreateExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) {
    suspend operator fun invoke(expense: ExpenseModel) {
        expenseRepository.createExpense(expense)
    }
}

class GetExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) {
    operator fun invoke(): Flow<List<ExpenseModel>> {
        return expenseRepository.getExpenses()
    }
}

class GetExpenseByIdUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) {
    suspend operator fun invoke(expenseId: Int): ExpenseModel {
        return expenseRepository.getExpenseById(expenseId)
    }
}

class UpdateExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) {
    suspend operator fun invoke(expense: ExpenseModel) {
        expenseRepository.updateExpense(expense)
    }
}

class DeleteExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) {
    suspend operator fun invoke(expenseId: Int) {
        expenseRepository.deleteExpense(expenseId)
    }
}

class FilterExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) {
    operator fun invoke(
        search: String?,
        category: String?,
        minAmount: Double?,
        maxAmount: Double?,
    ): Flow<List<ExpenseModel>> {
        return expenseRepository.filterExpenses(
            search,
            category,
            minAmount,
            maxAmount
        )
    }
}
