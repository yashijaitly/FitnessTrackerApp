package com.example.fitness_tracker_app.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitness_tracker_app.WorkoutViewModel
import com.example.fitness_tracker_app.data.database.entities.WorkoutExercise

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
    workoutViewModel: WorkoutViewModel,
    onNavigateBack: () -> Unit
) {
    val currentWorkout by workoutViewModel.currentWorkout.observeAsState()
    val timerSeconds by workoutViewModel.timerSeconds.observeAsState(0)
    val isTimerRunning by workoutViewModel.isTimerRunning.observeAsState(false)
    val allExercises by workoutViewModel.allExercises.observeAsState(emptyList())
    val workoutExercises by workoutViewModel.workoutExercises.observeAsState(emptyList())
    val selectedExercise by workoutViewModel.selectedExercise.observeAsState()

    var showExerciseDialog by remember { mutableStateOf(false) }
    var exerciseDuration by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = if (currentWorkout != null) "Active Workout" else "Start Workout",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (currentWorkout != null) {
            // Timer Display
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = workoutViewModel.formatTime(timerSeconds),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (currentWorkout?.status == "active") {
                            Button(
                                onClick = { workoutViewModel.pauseWorkout() }
                            ) {
                                Icon(Icons.Default.Pause, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Pause")
                            }
                        } else {
                            Button(
                                onClick = { workoutViewModel.resumeWorkout() }
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Resume")
                            }
                        }

                        OutlinedButton(
                            onClick = {
                                workoutViewModel.completeWorkout()
                                onNavigateBack()
                            }
                        ) {
                            Icon(Icons.Default.Stop, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Finish")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Add Exercise Button
            Button(
                onClick = { showExerciseDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Exercise")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Exercise List
            Text(
                text = "Exercises (${workoutExercises.size})",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(workoutExercises) { workoutExercise ->
                    ExerciseCard(workoutExercise = workoutExercise)
                }
            }
        } else {
            // Start Workout Button
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ready to start your workout?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            // Note: This would need user ID from somewhere
                            workoutViewModel.startWorkout(userId = 1L)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Start Workout")
                    }
                }
            }
        }
    }

    // Exercise Selection Dialog
    if (showExerciseDialog) {
        AlertDialog(
            onDismissRequest = { showExerciseDialog = false },
            title = { Text("Add Exercise") },
            text = {
                Column {
                    LazyColumn(
                        modifier = Modifier.height(200.dp)
                    ) {
                        items(allExercises) { exercise ->
                            Card(
                                onClick = {
                                    workoutViewModel.selectExercise(exercise)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = if (selectedExercise?.id == exercise.id)
                                    CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                                else CardDefaults.cardColors()
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Text(
                                        text = exercise.name,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = exercise.category,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = exerciseDuration,
                        onValueChange = { exerciseDuration = it },
                        label = { Text("Duration (minutes)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedExercise?.let { exercise ->
                            val duration = exerciseDuration.toIntOrNull() ?: 0
                            if (duration > 0) {
                                workoutViewModel.addExerciseToWorkout(exercise, duration)
                                showExerciseDialog = false
                                exerciseDuration = ""
                            }
                        }
                    },
                    enabled = selectedExercise != null && exerciseDuration.toIntOrNull() != null
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExerciseDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ExerciseCard(
    workoutExercise: WorkoutExercise
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Exercise #${workoutExercise.exerciseId}",
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${workoutExercise.duration} min",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Text(
                text = "${workoutExercise.calories?.toInt() ?: 0} cal",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}