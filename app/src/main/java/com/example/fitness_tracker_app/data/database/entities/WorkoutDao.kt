package com.example.fitness_tracker_app.data.database.entities

import androidx.room.*
import androidx.lifecycle.LiveData
import com.example.fitness_tracker_app.data.database.entities.Workout

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts WHERE userId = :userId ORDER BY createdAt DESC")
    fun getUserWorkouts(userId: Long): LiveData<List<Workout>>

    @Query("SELECT * FROM workouts WHERE userId = :userId AND status = 'active' LIMIT 1")
    suspend fun getActiveWorkout(userId: Long): Workout?

    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    suspend fun getWorkoutById(workoutId: Long): Workout?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout): Long

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Query("SELECT COUNT(*) FROM workouts WHERE userId = :userId AND status = 'completed'")
    suspend fun getTotalWorkouts(userId: Long): Int

    @Query("SELECT SUM(totalCalories) FROM workouts WHERE userId = :userId AND status = 'completed'")
    suspend fun getTotalCalories(userId: Long): Float?

    @Query("SELECT SUM(duration) FROM workouts WHERE userId = :userId AND status = 'completed'")
    suspend fun getTotalMinutes(userId: Long): Int?
}
