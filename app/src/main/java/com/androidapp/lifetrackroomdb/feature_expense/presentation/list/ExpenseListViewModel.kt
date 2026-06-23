package com.androidapp.lifetrackroomdb.feature_expense.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidapp.lifetrackroomdb.feature_expense.domain.usecase.ExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val expenseUseCase: ExpenseUseCase
) : ViewModel() {
    private val filterState = MutableStateFlow(ExpenseListUiState(isLoading = true))

    val uiState: StateFlow<ExpenseListUiState> = filterState
        .flatMapLatest { filter ->
            expenseUseCase.filterExpenses(
                search = filter.search?.ifBlank { null },
                category = filter.category,
                minAmount = filter.minAmount?.toDoubleOrNull(),
                maxAmount = filter.maxAmount?.toDoubleOrNull(),
            ).map { expenses ->
                filter.copy(
                    expenses = expenses,
                    isLoading = false
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ExpenseListUiState(isLoading = true)
        )

    fun onSearchChange(value: String) {
        filterState.value = filterState.value.copy(search = value)
    }

    fun onCategoryChange(value: String?) {
        filterState.value = filterState.value.copy(category = value)
    }

    fun onMinAmountChange(value: String) {
        filterState.value = filterState.value.copy(minAmount = value)
    }

    fun onMaxAmountChange(value: String) {
        filterState.value = filterState.value.copy(maxAmount = value)
    }

    fun deleteExpense(expenseId: Int) {
        viewModelScope.launch {
            expenseUseCase.deleteExpense(expenseId)
        }
    }
}
