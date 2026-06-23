package com.androidapp.lifetrackroomdb.feature_expense.presentation.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.androidapp.lifetrackroomdb.feature_expense.domain.model.ExpenseCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseFormScreen(
    expenseId: Int?,
    viewModel: ExpenseFormViewModel,
    onSaved: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(expenseId) {
        viewModel.loadExpense(expenseId)
    }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onSaved()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.title,
            onValueChange = viewModel::onTitleChange,
            label = { Text("Title") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.amount,
            onValueChange = viewModel::onAmountChange,
            label = { Text("Amount") }
        )

        Text("Category")

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExpenseCategory.entries.forEach { category ->
                FilterChip(
                    selected = state.category == category.name,
                    onClick = { viewModel.onCategoryChange(category.name) },
                    label = { Text(category.name) }
                )
            }
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.note,
            onValueChange = viewModel::onNoteChange,
            label = { Text("Note") },
            minLines = 3
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = viewModel::saveExpense
        ) {
            Text(if (expenseId == null) "Create Expense" else "Update Expense")
        }
    }
}