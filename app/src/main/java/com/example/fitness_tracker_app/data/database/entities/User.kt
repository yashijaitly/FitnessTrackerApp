package com.example.fitness_tracker_app.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0,
    val username:String,
    val email:String,
    val firstName:String?=null,
    val lastName:String?=null,
    val weight:Float?=null,
    val height:String?=null,
    val fitnessGoal:String?=null,
    val createdAt:Long=System.currentTimeMillis()
)
