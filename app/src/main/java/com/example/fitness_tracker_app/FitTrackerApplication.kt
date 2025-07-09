package com.example.fitness_tracker_app
import android.app.Application
import com.example.fitness_tracker_app.data.database.entities.FitTrackerDatabase
import com.example.fitness_tracker_app.FitTrackerRepository

class FitTrackerApplication : Application() {

    val database by lazy { FitTrackerDatabase.getDatabase(this) }
    val repository by lazy { FitTrackerRepository(database) }

    override fun onCreate() {
        super.onCreate()
    }
}