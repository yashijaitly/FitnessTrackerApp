package com.example.fitness_tracker_app.data.database.entities

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.fitness_tracker_app.data.database.entities.*

@Database(
    entities = [
        User::class,
        Workout::class,
        Exercise::class,
        WorkoutExercise::class,
        TutorialVideo::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FitTrackerDatabase :RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutExerciseDao(): WorkoutExerciseDao
    abstract fun tutorialVideoDao(): TutorialVideoDao

    companion object {
        @Volatile
        private var INSTANCE: FitTrackerDatabase? = null

        fun getDatabase(context: Context): FitTrackerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FitTrackerDatabase::class.java,
                    "fittracker_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}