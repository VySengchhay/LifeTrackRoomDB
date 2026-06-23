package com.androidapp.lifetrackroomdb.feature_task.presentation.form

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
import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskPriority
import com.androidapp.lifetrackroomdb.feature_task.domain.model.TaskStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    taskId: Int?,
    viewModel: TaskFormViewModel,
    onSaved: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
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
            value = state.description,
            onValueChange = viewModel::onDescriptionChange,
            label = { Text("Description") },
            minLines = 3
        )

        Text("Status")

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TaskStatus.entries.forEach { status ->
                FilterChip(
                    selected = state.status == status.name,
                    onClick = { viewModel.onStatusChange(status.name) },
                    label = { Text(status.name) }
                )
            }
        }

        Text("Priority")

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TaskPriority.entries.forEach { priority ->
                FilterChip(
                    selected = state.priority == priority.name,
                    onClick = { viewModel.onPriorityChange(priority.name) },
                    label = { Text(priority.name) }
                )
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = viewModel::saveTask
        ) {
            Text(if (taskId == null) "Create Task" else "Update Task")
        }
    }
}