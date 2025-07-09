package com.example.fitness_tracker_app.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val category: String,
    val metValue: Float,
    val description: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
