package com.androidapp.lifetrackroomdb.core.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.androidapp.lifetrackroomdb.R
import com.androidapp.lifetrackroomdb.feature_dashboard.presentation.DashboardScreen
import com.androidapp.lifetrackroomdb.feature_expense.presentation.detail.ExpenseDetailScreen
import com.androidapp.lifetrackroomdb.feature_expense.presentation.form.ExpenseFormScreen
import com.androidapp.lifetrackroomdb.feature_expense.presentation.list.ExpenseListScreen
import com.androidapp.lifetrackroomdb.feature_task.presentation.detail.TaskDetailScreen
import com.androidapp.lifetrackroomdb.feature_task.presentation.form.TaskFormScreen
import com.androidapp.lifetrackroomdb.feature_task.presentation.list.TaskListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LifeTrackNavigation(
    modifier: Modifier = Modifier
) {
    val backStack = remember {
        mutableStateListOf<AppRoute>(AppRoute.Dashboard)
    }

    val currentRoute = backStack.last()

    val isTopLevel =
        currentRoute is AppRoute.Dashboard ||
        currentRoute is AppRoute.Tasks ||
        currentRoute is AppRoute.Expenses

    BackHandler(backStack.size > 1) {
        backStack.removeLastOrNull()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        titleForRoute(currentRoute)
                    )
                },
                navigationIcon = {
                    if (!isTopLevel) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "Back",
                            modifier = Modifier
                                .clickable {
                                    backStack.removeLastOrNull()
                                }
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (isTopLevel) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    bottomDestinations.forEach { destination ->
                        NavigationBarItem(
                            selected = currentRoute == destination.route,
                            onClick = {
                                backStack.add(destination.route)
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(destination.icon),
                                    contentDescription = destination.label
                                )
                            },
                            label = {
                                Text(text = destination.label)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedTextColor = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            when (currentRoute) {
                is AppRoute.Tasks -> {
                    FloatingActionButton(
                        onClick = {
                            backStack.add(AppRoute.TaskForm())
                        }
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Task"
                        )
                    }
                }

                is AppRoute.Expenses -> {
                    FloatingActionButton(
                        onClick = {
                            backStack.add(AppRoute.ExpenseForm())
                        }
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Expense"
                        )
                    }
                }

                else -> {}
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = backStack,
            onBack = {
                backStack.removeLastOrNull()
            },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = { route ->
                when (route) {
                    is AppRoute.Dashboard -> NavEntry(route) {
                        DashboardScreen(
                            viewModel = hiltViewModel(),
                            onOpenTasks = {
                                backStack.clear()
                                backStack.add(AppRoute.Tasks)
                            },
                            onOpenExpenses = {
                                backStack.clear()
                                backStack.add(AppRoute.Expenses)
                            }
                        )
                    }

                    is AppRoute.Tasks -> NavEntry(route) {
                        TaskListScreen(
                            viewModel = hiltViewModel(),
                            onOpenDetail = { taskId ->
                                backStack.add(AppRoute.TaskDetail(taskId))
                            }
                        )
                    }

                    is AppRoute.TaskDetail -> NavEntry(route) {
                        TaskDetailScreen(
                            taskId = route.taskId,
                            viewModel = hiltViewModel(),
                            onBack = {
                                backStack.removeLastOrNull()
                            },
                            onEdit = { taskId ->
                                backStack.add(AppRoute.TaskForm(taskId))
                            }
                        )
                    }

                    is AppRoute.TaskForm -> NavEntry(route) {
                        TaskFormScreen(
                            taskId = route.taskId,
                            viewModel = hiltViewModel(),
                            onSaved = {
                                backStack.removeLastOrNull()
                            }
                        )
                    }

                    is AppRoute.Expenses -> NavEntry(route) {
                        ExpenseListScreen(
                            viewModel = hiltViewModel(),
                            onOpenDetail = { expenseId ->
                                backStack.add(AppRoute.ExpenseDetail(expenseId))
                            }
                        )
                    }

                    is AppRoute.ExpenseDetail -> NavEntry(route) {
                        ExpenseDetailScreen(
                            expenseId = route.expenseId,
                            viewModel = hiltViewModel(),
                            onBack = {
                                backStack.removeLastOrNull()
                            },
                            onEdit = { expenseId ->
                                backStack.add(AppRoute.ExpenseForm(expenseId))
                            }
                        )
                    }

                    is AppRoute.ExpenseForm -> NavEntry(route) {
                        ExpenseFormScreen(
                            expenseId = route.expenseId,
                            viewModel = hiltViewModel(),
                            onSaved = {
                                backStack.removeLastOrNull()
                            }
                        )
                    }
                }
            }
        )
    }
}

private fun titleForRoute(route: AppRoute): String {
    return when (route) {
        AppRoute.Dashboard -> "LifeTrack"
        AppRoute.Tasks -> "Tasks"
        AppRoute.Expenses -> "Expenses"
        is AppRoute.TaskDetail -> "Task Detail"
        is AppRoute.TaskForm -> if (route.taskId == null) "Add Task" else "Edit Task"
        is AppRoute.ExpenseDetail -> "Expense Detail"
        is AppRoute.ExpenseForm -> if (route.expenseId == null) "Add Expense" else "Edit Expense"
    }
}