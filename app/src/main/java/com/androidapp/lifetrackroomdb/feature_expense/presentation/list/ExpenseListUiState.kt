package com.androidapp.lifetrackroomdb.feature_expense.presentation.list

import com.androidapp.lifetrackroomdb.feature_expense.domain.model.ExpenseModel

data class ExpenseListUiState (
    val expenses: List<ExpenseModel> = emptyList(),
    val search: String? = "",
    val category: String? = null,
    val minAmount: String? = "",
    val maxAmount: String? = "",
    val isLoading: Boolean = false,
)