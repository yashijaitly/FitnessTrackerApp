package com.example.fitness_tracker_app.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitness_tracker_app.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    mainViewModel: MainViewModel,
    onNavigateBack: () -> Unit
) {
    val currentUser by mainViewModel.currentUser.observeAsState()
    val userStats by mainViewModel.userStats.observeAsState()
    val isLoading by mainViewModel.isLoading.observeAsState(false)

    var isEditing by remember { mutableStateOf(false) }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var fitnessGoal by remember { mutableStateOf("") }

    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            firstName = user.firstName ?: ""
            lastName = user.lastName ?: ""
            weight = user.weight?.toString() ?: ""
            height = user.height ?: ""
            fitnessGoal = user.fitnessGoal ?: ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
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
                text = "Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { isEditing = !isEditing }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Picture Section
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = currentUser?.username ?: "User",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = currentUser?.email ?: "",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Stats Section
        Text(
            text = "Statistics",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(
                title = "Total Workouts",
                value = userStats?.totalWorkouts?.toString() ?: "0",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Total Calories",
                value = "${userStats?.totalCalories?.toInt() ?: 0}",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        StatCard(
            title = "Total Minutes Exercised",
            value = "${userStats?.totalMinutes ?: 0}",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Details Section
        Text(
            text = "Details",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)

            ) {
                if (isEditing) {
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = { Text("Weight (kg)") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    OutlinedTextField(
                        value = height,
                        onValueChange = { height = it },
                        label = { Text("Height (cm)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = fitnessGoal,
                        onValueChange = { fitnessGoal = it },
                        label = { Text("Fitness Goal") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { isEditing = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel")
                        }

                        Button(
                            onClick = {
                                currentUser?.let { user ->
                                    val updatedUser = user.copy(
                                        firstName = firstName.takeIf { it.isNotBlank() },
                                        lastName = lastName.takeIf { it.isNotBlank() },
                                        weight = weight.toFloatOrNull(),
                                        height = height.takeIf { it.isNotBlank() },
                                        fitnessGoal = fitnessGoal.takeIf { it.isNotBlank() }
                                    )
                                    mainViewModel.updateUser(updatedUser)
                                    isEditing = false
                                }
                            },
                            modifier = Modifier.weight(1f),
                            enabled = !isLoading
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(modifier = Modifier.size(16.dp))
                            } else {
                                Text("Save")
                            }
                        }
                    }
                } else {
                    ProfileDetailRow("First Name", currentUser?.firstName ?: "Not set")
                    ProfileDetailRow("Last Name", currentUser?.lastName ?: "Not set")
                    ProfileDetailRow("Weight", if (currentUser?.weight != null) "${ currentUser?.weight} kg" else "Not set")
                    ProfileDetailRow("Height", currentUser?.height ?: "Not set")
                    ProfileDetailRow("Fitness Goal", currentUser?.fitnessGoal ?: "Not set")
                }
            }
        }
    }
}

@Composable
fun ProfileDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Medium
        )
    }
}