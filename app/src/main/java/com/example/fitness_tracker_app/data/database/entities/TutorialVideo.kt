package com.example.fitness_tracker_app.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tutorial_videos")
data class TutorialVideo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val youtubeId: String,
    val category: String,
    val duration: String? = null,
    val thumbnailUrl: String? = null,
    val viewCount: Int = 0,
    val rating: Float = 0f,
    val createdAt: Long = System.currentTimeMillis()
)
