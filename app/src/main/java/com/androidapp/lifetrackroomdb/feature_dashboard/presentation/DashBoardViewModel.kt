package com.androidapp.lifetrackroomdb.feature_dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidapp.lifetrackroomdb.feature_expense.domain.model.ExpenseModel
import com.androidapp.lifetrackroomdb.feature_expense.domain.usecase.ExpenseUseCase
import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskModel
import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskStatus
import com.androidapp.lifetrackroomdb.feature_task.domain.usecase.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class DashboardUiState(
    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val pendingTasks: Int = 0,
    val totalExpenses: Double = 0.0,
    val recentTasks: List<TaskModel> = emptyList(),
    val recentExpenses: List<ExpenseModel> = emptyList()
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    taskUseCase: TaskUseCase,
    expenseUseCase: ExpenseUseCase
) : ViewModel() {

    val uiState: StateFlow<DashboardUiState> = combine(
        taskUseCase.getTasks(),
        expenseUseCase.getExpenses()
    ) { tasks, expenses ->
        DashboardUiState(
            totalTasks = tasks.size,
            completedTasks = tasks.count { it.status == TaskStatus.DONE.name },
            pendingTasks = tasks.count { it.status != TaskStatus.DONE.name },
            totalExpenses = expenses.sumOf { it.amount },
            recentTasks = tasks.take(3),
            recentExpenses = expenses.take(3)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DashboardUiState()
    )
}