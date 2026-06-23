package com.androidapp.lifetrackroomdb.feature_expense.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ExpenseDetailScreen(
    expenseId: Int,
    viewModel: ExpenseDetailViewModel,
    onBack: () -> Unit,
    onEdit: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(expenseId) {
        viewModel.loadExpense(expenseId)
    }

    LaunchedEffect(state.isDeleted) {
        if (state.isDeleted) {
            onBack()
        }
    }

    if (state.isLoading) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth()
        )
        return
    }

    val expense = state.expense

    if (expense == null) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Expense not found"
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = expense.title,
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "$${expense.amount}",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = expense.note,
                    style = MaterialTheme.typography.bodyLarge
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AssistChip(
                        onClick = {},
                        label = { Text(expense.category) }
                    )
                }
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onEdit(expense.id)
            }
        ) {
            Text("Edit")
        }

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = viewModel::deleteExpense
        ) {
            Text("Delete")
        }
    }
}