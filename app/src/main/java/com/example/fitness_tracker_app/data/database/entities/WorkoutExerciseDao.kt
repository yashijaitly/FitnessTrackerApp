package com.example.fitness_tracker_app.data.database.entities

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fitness_tracker_app.data.database.entities.WorkoutExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutExercise(workoutExercise: WorkoutExercise): Long

    @Update
    suspend fun updateWorkoutExercise(workoutExercise: WorkoutExercise)

    @Delete
    suspend fun deleteWorkoutExercise(workoutExercise: WorkoutExercise)

    @Query("SELECT * FROM workout_exercises WHERE id = :id")
    suspend fun getWorkoutExerciseById(id: Long): WorkoutExercise?

    @Query("SELECT * FROM workout_exercises WHERE workoutId = :workoutId")
    fun getExercisesForWorkout(workoutId: Long): LiveData<List<WorkoutExercise>>

    @Query("SELECT * FROM workout_exercises ORDER BY createdAt DESC")
    fun getAllWorkoutExercises(): Flow<List<WorkoutExercise>>

    @Query("DELETE FROM workout_exercises")
    suspend fun clearAll()
}