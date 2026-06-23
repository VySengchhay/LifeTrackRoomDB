package com.androidapp.lifetrackroomdb.feature_expense.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidapp.lifetrackroomdb.feature_expense.domain.model.ExpenseModel
import com.androidapp.lifetrackroomdb.feature_expense.domain.usecase.ExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ExpenseDetailUiState(
    val expense: ExpenseModel? = null,
    val isLoading: Boolean = true,
    val isDeleted: Boolean = false
)

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val expenseUseCase: ExpenseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseDetailUiState())
    val uiState: StateFlow<ExpenseDetailUiState> = _uiState

    fun loadExpense(expenseId: Int) {
        viewModelScope.launch {
            val expense = expenseUseCase.getExpenseById(expenseId)
            _uiState.value = ExpenseDetailUiState(
                expense = expense,
                isLoading = false
            )
        }
    }

    fun deleteExpense() {
        val expenseId = _uiState.value.expense?.id ?: return

        viewModelScope.launch {
            expenseUseCase.deleteExpense(expenseId)
            _uiState.value = _uiState.value.copy(isDeleted = true)
        }
    }
}