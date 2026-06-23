package com.androidapp.lifetrackroomdb.feature_task.presentation.detail

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
fun TaskDetailScreen(
    taskId: Int,
    viewModel: TaskDetailViewModel,
    onBack: () -> Unit,
    onEdit: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
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

    val task = state.task

    if (task == null) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Task not found"
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
                    text = task.title,
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyLarge
                )

                FlowRow(
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
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onEdit(task.id)
            }
        ) {
            Text("Edit")
        }

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = viewModel::deleteTask
        ) {
            Text("Delete")
        }
    }
}