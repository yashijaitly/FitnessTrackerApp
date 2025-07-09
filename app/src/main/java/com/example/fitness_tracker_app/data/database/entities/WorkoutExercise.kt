package com.example.fitness_tracker_app.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_exercises")
data class WorkoutExercise(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val workoutId: Long,
    val exerciseId: Long,
    val duration: Int? = null, // in minutes
    val calories: Float? = null,
    val createdAt: Long = System.currentTimeMillis()
)
