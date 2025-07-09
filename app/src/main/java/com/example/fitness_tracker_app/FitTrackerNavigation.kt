package com.example.fitness_tracker_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitness_tracker_app.Screen.Dashboard
import com.example.fitness_tracker_app.Screen.Profile
import com.example.fitness_tracker_app.Screen.Welcome
import com.example.fitness_tracker_app.Screen.Workout
import com.example.fitness_tracker_app.ui.theme.screens.WelcomeScreen
import com.example.fitness_tracker_app.ui.theme.screens.DashboardScreen
import com.example.fitness_tracker_app.ui.theme.screens.WorkoutScreen
import com.example.fitness_tracker_app.ui.theme.screens.ProfileScreen
import com.example.fitness_tracker_app.ui.theme.screens.TutorialsScreen

import com.example.fitness_tracker_app.Screen.Tutorials
import com.example.fitness_tracker_app.MainViewModel
import com.example.fitness_tracker_app.WorkoutViewModel

@Composable
fun FitTrackerNavigation(
    mainViewModel: MainViewModel,
    workoutViewModel: WorkoutViewModel,
    navController: NavHostController = rememberNavController()
) {
    val currentUser by mainViewModel.currentUser.observeAsState()

    val startDestination = if (currentUser != null) {
        Screen.Dashboard.route
    } else {
        Screen.Welcome.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onUserCreated = { username, email, firstName, lastName ->
                    mainViewModel.createUser(username, email, firstName, lastName)
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                mainViewModel = mainViewModel,
                workoutViewModel = workoutViewModel,
                onNavigateToWorkout = {
                    navController.navigate(Screen.Workout.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onNavigateToTutorials = {
                    navController.navigate(Screen.Tutorials.route)
                }
            )
        }

        composable(Screen.Workout.route) {
            WorkoutScreen(
                workoutViewModel = workoutViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                mainViewModel = mainViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Tutorials.route) {
            TutorialsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Dashboard : Screen("dashboard")
    object Workout : Screen("workout")
    object Profile : Screen("profile")
    object Tutorials : Screen("tutorials")
}