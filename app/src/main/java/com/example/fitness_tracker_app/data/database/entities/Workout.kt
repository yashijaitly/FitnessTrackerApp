package com.example.fitness_tracker_app.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0,
    val userId:Long,
    val startTime:Long,
    val endTime:Long?=null,
    val duration:Int?=null,
    val totalCalories:Float?=null,
    val status:String="active",
    val createdAt:Long=System.currentTimeMillis()

)
