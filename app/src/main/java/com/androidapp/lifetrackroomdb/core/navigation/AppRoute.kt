package com.androidapp.lifetrackroomdb.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute : NavKey {
    @Serializable
    data object Dashboard : AppRoute

    @Serializable
    data object Tasks : AppRoute

    @Serializable
    data class TaskDetail(val taskId: Int) : AppRoute

    @Serializable
    data class TaskForm(val taskId: Int? = null) : AppRoute

    @Serializable
    data object Expenses : AppRoute

    @Serializable
    data class ExpenseDetail(val expenseId: Int) : AppRoute

    @Serializable
    data class ExpenseForm(val expenseId: Int? = null) : AppRoute
}