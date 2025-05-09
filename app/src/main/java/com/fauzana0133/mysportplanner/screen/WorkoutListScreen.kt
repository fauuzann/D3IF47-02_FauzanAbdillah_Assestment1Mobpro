package com.fauzana0133.mysportplanner.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fauzana0133.mysportplanner.model.Workout
import com.fauzana0133.mysportplanner.viewmodel.WorkoutViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutListScreen(viewModel: WorkoutViewModel) {
    val workoutList = viewModel.workoutList.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Jadwal Olahraga") }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.padding(16.dp)
        ) {
            items(workoutList) { workout ->
                WorkoutItem(
                    workout = workout,
                    onDelete = { viewModel.delete(workout) },
                    onEdit = { updated -> viewModel.update(updated) }
                )
            }
        }
    }
}

@Composable
fun WorkoutItem(
    workout: Workout,
    onDelete: () -> Unit,
    onEdit: (Workout) -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Nama: ${workout.name}", style = MaterialTheme.typography.titleMedium)
            Text("Olahraga: ${workout.sport}")
            Text("Hari: ${workout.days}")

            Row(modifier = Modifier.padding(top = 8.dp)) {
                Button(onClick = { showEditDialog = true }) {
                    Text("Edit")
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(onClick = { showDeleteDialog = true }) {
                    Text("Hapus")
                }
            }
        }
    }

    if (showEditDialog) {
        EditDialog(
            workout = workout,
            onConfirm = {
                onEdit(it)
                showEditDialog = false
            },
            onDismiss = { showEditDialog = false }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah kamu yakin ingin menghapus jadwal ini?") },
            confirmButton = {
                Button(onClick = {
                    onDelete()
                    showDeleteDialog = false
                }) {
                    Text("Ya")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDeleteDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun EditDialog(
    workout: Workout,
    onConfirm: (Workout) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(workout.name) }
    var sport by remember { mutableStateOf(workout.sport) }
    var days by remember { mutableStateOf(workout.days) }

    val isValid = name.isNotBlank() && sport.isNotBlank() && days.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Jadwal") },
        text = {
            Column {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nama") })
                OutlinedTextField(value = sport, onValueChange = { sport = it }, label = { Text("Olahraga") })
                OutlinedTextField(value = days, onValueChange = { days = it }, label = { Text("Hari") })
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (isValid) onConfirm(workout.copy(name = name, sport = sport, days = days))
                },
                enabled = isValid
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}