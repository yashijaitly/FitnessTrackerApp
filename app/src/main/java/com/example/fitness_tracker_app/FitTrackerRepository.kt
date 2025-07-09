package com.example.fitness_tracker_app

import androidx.lifecycle.LiveData
import com.example.fitness_tracker_app.data.database.entities.Exercise
import com.example.fitness_tracker_app.data.database.entities.FitTrackerDatabase
import com.example.fitness_tracker_app.data.database.entities.TutorialVideo
import com.example.fitness_tracker_app.data.database.entities.User
import com.example.fitness_tracker_app.data.database.entities.Workout
import com.example.fitness_tracker_app.data.database.entities.WorkoutExercise

class FitTrackerRepository(private val database: FitTrackerDatabase) {
    //User Operations
    fun getCurrentUser(): LiveData<User?> = database.userDao().getCurrentUser()

    suspend fun getUserById(userId: Long): User? = database.userDao().getUserById(userId)

    suspend fun insertUser(user: User): Long = database.userDao().insertUser(user)

    suspend fun updateUser(user: User) = database.userDao().updateUser(user)

    // Workout operations
    fun getUserWorkouts(userId: Long): LiveData<List<Workout>> =
        database.workoutDao().getUserWorkouts(userId)

    suspend fun getActiveWorkout(userId: Long): Workout? =
        database.workoutDao().getActiveWorkout(userId)

    suspend fun insertWorkout(workout: Workout): Long = database.workoutDao().insertWorkout(workout)

    suspend fun updateWorkout(workout: Workout) = database.workoutDao().updateWorkout(workout)

    suspend fun getTotalWorkouts(userId: Long): Int = database.workoutDao().getTotalWorkouts(userId)

    suspend fun getTotalCalories(userId: Long): Float? = database.workoutDao().getTotalCalories(userId)

    suspend fun getTotalMinutes(userId: Long): Int? = database.workoutDao().getTotalMinutes(userId)

    // Exercise operations
    fun getAllExercises(): LiveData<List<Exercise>> = database.exerciseDao().getAllExercises()

    fun getExercisesByCategory(category: String): LiveData<List<Exercise>> =
        database.exerciseDao().getExercisesByCategory(category)

    suspend fun insertExercises(exercises: List<Exercise>) = database.exerciseDao().insertExercises(exercises)

    suspend fun getExerciseById(exerciseId: Long): Exercise? = database.exerciseDao().getExerciseById(exerciseId)

    suspend fun getAllCategories(): List<String> = database.exerciseDao().getAllCategories()
    // Workout Exercise operations
    fun getWorkoutExercises(workoutId: Long): LiveData<List<WorkoutExercise>> =
        database.workoutExerciseDao().getExercisesForWorkout(workoutId)

    suspend fun insertWorkoutExercise(workoutExercise: WorkoutExercise): Long =
        database.workoutExerciseDao().insertWorkoutExercise(workoutExercise)

    suspend fun updateWorkoutExercise(workoutExercise: WorkoutExercise) =
        database.workoutExerciseDao().updateWorkoutExercise(workoutExercise)

    // Tutorial Video operations
    fun getAllTutorialVideos(): LiveData<List<TutorialVideo>> =
        database.tutorialVideoDao().getAllTutorialVideos()

    fun getTutorialVideosByCategory(category: String): LiveData<List<TutorialVideo>> =
        database.tutorialVideoDao().getTutorialVideosByCategory(category)

    suspend fun insertTutorialVideos(videos: List<TutorialVideo>) =
        database.tutorialVideoDao().insertTutorialVideos(videos)

    suspend fun getAllVideoCategories(): List<String> = database.tutorialVideoDao().getAllVideoCategories()
}

