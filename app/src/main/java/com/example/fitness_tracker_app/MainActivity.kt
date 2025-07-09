package com.example.fitness_tracker_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.fitness_tracker_app.data.database.entities.FitTrackerDatabase
import com.example.fitness_tracker_app.ui.theme.Fitness_Tracker_AppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var workoutViewModel: WorkoutViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Initialize database and repository
        val database = FitTrackerDatabase.getDatabase(this)
        val repository = FitTrackerRepository(database)

        // Initialize ViewModels
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(repository)
        )[MainViewModel::class.java]

        workoutViewModel = ViewModelProvider(
            this,
            WorkoutViewModelFactory(repository)
        )[WorkoutViewModel::class.java]

        // Seed initial data
        lifecycleScope.launch {
            val dataSeeder = DataSeeder(repository)
            dataSeeder.seedExercises()
            dataSeeder.seedTutorialVideos()
        }
        setContent {
            Fitness_Tracker_AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color= MaterialTheme.colorScheme.background
                ) {
                    FitTrackerNavigation(mainViewModel = mainViewModel,
                        workoutViewModel = workoutViewModel)
                }


            }
        }
    }
}

