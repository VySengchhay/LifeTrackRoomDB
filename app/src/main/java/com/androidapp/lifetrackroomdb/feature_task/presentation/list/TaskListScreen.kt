package com.androidapp.lifetrackroomdb.feature_task.presentation.list

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
import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskModel
import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskPriority
import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel,
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
                    Text("Search task")
                }
            )
        }

        FlowRow(
            modifier = Modifier.padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = state.status == null,
                onClick = { viewModel.onStatusChange(null) },
                label = { Text("All") }
            )

            TaskStatus.entries.forEach { status ->
                FilterChip(
                    selected = state.status == status.name,
                    onClick = { viewModel.onStatusChange(status.name) },
                    label = { Text(status.name) }
                )
            }
        }

        FlowRow(
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = state.priority == null,
                onClick = { viewModel.onPriorityChange(null) },
                label = { Text("Any Priority") }
            )

            TaskPriority.entries.forEach { priority ->
                FilterChip(
                    selected = state.priority == priority.name,
                    onClick = { viewModel.onPriorityChange(priority.name) },
                    label = { Text(priority.name) }
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
                items = state.tasks,
                key = { it.id }
            ) { task ->
                TaskItem(
                    task = task,
                    onClick = {
                        onOpenDetail(task.id)
                    },
                    onDelete = {
                        viewModel.deleteTask(task.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun TaskItem(
    task: TaskModel,
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
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AssistChip(
                        onClick = {},
                        label = { Text(task.status) }
                    )

                    AssistChip(
                        onClick = {},
                        label = { Text(task.priority) }
                    )
                }
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete task"
                )
            }
        }
    }
}