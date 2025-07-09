package com.example.fitness_tracker_app.data.database.entities

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fitness_tracker_app.data.database.entities.TutorialVideo
@Dao
interface TutorialVideoDao {
    @Query("SELECT * FROM tutorial_videos ORDER BY viewCount DESC")
    fun getAllTutorialVideos(): LiveData<List<TutorialVideo>>

    @Query("SELECT * FROM tutorial_videos WHERE category = :category ORDER BY viewCount DESC")
    fun getTutorialVideosByCategory(category: String): LiveData<List<TutorialVideo>>

    @Query("SELECT * FROM tutorial_videos WHERE id = :videoId")
    suspend fun getTutorialVideoById(videoId: Long): TutorialVideo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTutorialVideo(video: TutorialVideo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTutorialVideos(videos: List<TutorialVideo>)

    @Update
    suspend fun updateTutorialVideo(video: TutorialVideo)

    @Delete
    suspend fun deleteTutorialVideo(video: TutorialVideo)

    @Query("SELECT DISTINCT category FROM tutorial_videos ORDER BY category ASC")
    suspend fun getAllVideoCategories(): List<String>
}