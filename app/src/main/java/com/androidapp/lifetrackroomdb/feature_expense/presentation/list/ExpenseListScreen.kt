package com.androidapp.lifetrackroomdb.feature_expense.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.androidapp.lifetrackroomdb.feature_expense.domain.model.ExpenseCategory
import com.androidapp.lifetrackroomdb.feature_expense.domain.model.ExpenseModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListScreen(
    viewModel: ExpenseListViewModel,
    onOpenDetail: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        state.search?.let {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = it,
                onValueChange = viewModel::onSearchChange,
                label = {
                    Text("Search expense")
                }
            )
        }

        Row(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.minAmount?.let {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = it,
                    onValueChange = viewModel::onMinAmountChange,
                    label = { Text("Min") }
                )
            }

            state.maxAmount?.let {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = it,
                    onValueChange = viewModel::onMaxAmountChange,
                    label = { Text("Max") }
                )
            }
        }

        FlowRow(
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = state.category == null,
                onClick = { viewModel.onCategoryChange(null) },
                label = { Text("All") }
            )

            ExpenseCategory.entries.forEach { category ->
                FilterChip(
                    selected = state.category == category.name,
                    onClick = { viewModel.onCategoryChange(category.name) },
                    label = { Text(category.name) }
                )
            }
        }

        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = state.expenses,
                key = { it.id }
            ) { expense ->
                ExpenseItem(
                    expense = expense,
                    onClick = {
                        onOpenDetail(expense.id)
                    },
                    onDelete = {
                        viewModel.deleteExpense(expense.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun ExpenseItem(
    expense: ExpenseModel,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = expense.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "$${expense.amount}",
                    style = MaterialTheme.typography.bodyLarge
                )

                AssistChip(
                    onClick = {},
                    label = { Text(expense.category) }
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete expense"
                )
            }
        }
    }
}