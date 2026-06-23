package com.androidapp.lifetrackroomdb.feature_expense.presentation.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidapp.lifetrackroomdb.feature_expense.domain.model.ExpenseCategory
import com.androidapp.lifetrackroomdb.feature_expense.domain.model.ExpenseModel
import com.androidapp.lifetrackroomdb.feature_expense.domain.usecase.ExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ExpenseFormUiState(
    val id: Int? = null,
    val title: String = "",
    val amount: String = "",
    val category: String = ExpenseCategory.FOOD.name,
    val note: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val isSaved: Boolean = false
)

@HiltViewModel
class ExpenseFormViewModel @Inject constructor(
    private val expenseUseCase: ExpenseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseFormUiState())
    val uiState: StateFlow<ExpenseFormUiState> = _uiState

    fun loadExpense(expenseId: Int?) {
        if (expenseId == null) return

        viewModelScope.launch {
            val expense = expenseUseCase.getExpenseById(expenseId) ?: return@launch
            _uiState.value = ExpenseFormUiState(
                id = expense.id,
                title = expense.title,
                amount = expense.amount.toString(),
                category = expense.category,
                note = expense.note,
                createdAt = expense.createdAt
            )
        }
    }

    fun onTitleChange(value: String) {
        _uiState.value = _uiState.value.copy(title = value)
    }

    fun onAmountChange(value: String) {
        _uiState.value = _uiState.value.copy(amount = value)
    }

    fun onCategoryChange(value: String) {
        _uiState.value = _uiState.value.copy(category = value)
    }

    fun onNoteChange(value: String) {
        _uiState.value = _uiState.value.copy(note = value)
    }

    fun saveExpense() {
        val state = _uiState.value
        val amount = state.amount.toDoubleOrNull() ?: return

        if (state.title.isBlank()) return

        viewModelScope.launch {
            val expense = ExpenseModel(
                id = state.id ?: 0,
                title = state.title.trim(),
                amount = amount,
                category = state.category,
                note = state.note.trim(),
                createdAt = state.createdAt
            )

            if (state.id == null) {
                expenseUseCase.createExpense(expense)
            } else {
                expenseUseCase.updateExpense(expense)
            }

            _uiState.value = state.copy(isSaved = true)
        }
    }
}