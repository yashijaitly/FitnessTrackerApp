package com.example.fitness_tracker_app
import com.example.fitness_tracker_app.data.database.entities.Exercise
import com.example.fitness_tracker_app.data.database.entities.TutorialVideo
import com.example.fitness_tracker_app.FitTrackerRepository

class DataSeeder(private val repository: FitTrackerRepository) {

    suspend fun seedExercises() {
        val exercises = listOf(
            // Strength exercises
            Exercise(name = "Push-ups", category = "Strength", metValue = 3.8f, description = "Upper body bodyweight exercise"),
            Exercise(name = "Pull-ups", category = "Strength", metValue = 8.0f, description = "Upper body pulling exercise"),
            Exercise(name = "Squats", category = "Strength", metValue = 5.0f, description = "Lower body compound exercise"),
            Exercise(name = "Lunges", category = "Strength", metValue = 4.0f, description = "Single leg strengthening exercise"),
            Exercise(name = "Planks", category = "Strength", metValue = 3.0f, description = "Core stability exercise"),
            Exercise(name = "Burpees", category = "Strength", metValue = 8.0f, description = "Full body explosive exercise"),
            Exercise(name = "Deadlifts", category = "Strength", metValue = 6.0f, description = "Posterior chain strengthening"),
            Exercise(name = "Bench Press", category = "Strength", metValue = 5.0f, description = "Upper body pressing movement"),

            // Cardio exercises
            Exercise(name = "Running", category = "Cardio", metValue = 8.0f, description = "Cardiovascular endurance exercise"),
            Exercise(name = "Cycling", category = "Cardio", metValue = 7.5f, description = "Low impact cardio exercise"),
            Exercise(name = "Swimming", category = "Cardio", metValue = 8.0f, description = "Full body cardiovascular exercise"),
            Exercise(name = "Jumping Jacks", category = "Cardio", metValue = 7.0f, description = "High intensity cardio movement"),
            Exercise(name = "Mountain Climbers", category = "Cardio", metValue = 8.0f, description = "Dynamic cardio exercise"),
            Exercise(name = "High Knees", category = "Cardio", metValue = 7.0f, description = "Cardio warm-up exercise"),
            Exercise(name = "Jump Rope", category = "Cardio", metValue = 8.8f, description = "Coordination and cardio exercise"),

            // Yoga exercises
            Exercise(name = "Sun Salutation", category = "Yoga", metValue = 2.5f, description = "Dynamic yoga flow sequence"),
            Exercise(name = "Warrior Pose", category = "Yoga", metValue = 2.0f, description = "Standing strength pose"),
            Exercise(name = "Downward Dog", category = "Yoga", metValue = 2.3f, description = "Inversion and stretching pose"),
            Exercise(name = "Tree Pose", category = "Yoga", metValue = 2.0f, description = "Balance and focus pose"),
            Exercise(name = "Child's Pose", category = "Yoga", metValue = 1.5f, description = "Restorative resting pose"),
            Exercise(name = "Cobra Pose", category = "Yoga", metValue = 2.0f, description = "Back strengthening pose"),

            // Flexibility exercises
            Exercise(name = "Hamstring Stretch", category = "Flexibility", metValue = 1.5f, description = "Posterior leg flexibility"),
            Exercise(name = "Quad Stretch", category = "Flexibility", metValue = 1.5f, description = "Anterior leg flexibility"),
            Exercise(name = "Shoulder Stretch", category = "Flexibility", metValue = 1.5f, description = "Upper body mobility"),
            Exercise(name = "Hip Flexor Stretch", category = "Flexibility", metValue = 1.5f, description = "Hip mobility exercise"),
            Exercise(name = "Calf Stretch", category = "Flexibility", metValue = 1.5f, description = "Lower leg flexibility"),
            Exercise(name = "Neck Stretch", category = "Flexibility", metValue = 1.3f, description = "Cervical spine mobility")
        )

        repository.insertExercises(exercises)
    }

    suspend fun seedTutorialVideos() {
        val tutorialVideos = listOf(
            TutorialVideo(
                title = "Perfect Push-Up Form",
                description = "Learn the proper technique for push-ups to maximize effectiveness and prevent injury",
                youtubeId = "IODxDxX7oi4",
                category = "Strength",
                duration = "5:32",
                thumbnailUrl = "https://img.youtube.com/vi/IODxDxX7oi4/maxresdefault.jpg",
                viewCount = 1250000,
                rating = 4.8f
            ),
            TutorialVideo(
                title = "Beginner Yoga Flow",
                description = "A gentle 15-minute yoga sequence perfect for beginners",
                youtubeId = "v7AYKMP6rOE",
                category = "Yoga",
                duration = "15:24",
                thumbnailUrl = "https://img.youtube.com/vi/v7AYKMP6rOE/maxresdefault.jpg",
                viewCount = 892000,
                rating = 4.9f
            ),
            TutorialVideo(
                title = "HIIT Cardio Workout",
                description = "High-intensity interval training to burn calories and improve cardiovascular health",
                youtubeId = "ml6cT4AZdqI",
                category = "Cardio",
                duration = "20:15",
                thumbnailUrl = "https://img.youtube.com/vi/ml6cT4AZdqI/maxresdefault.jpg",
                viewCount = 567000,
                rating = 4.7f
            ),
            TutorialVideo(
                title = "Squats: Form and Variations",
                description = "Master the squat movement with proper form and learn different variations",
                youtubeId = "aclHkVaku9U",
                category = "Strength",
                duration = "8:45",
                thumbnailUrl = "https://img.youtube.com/vi/aclHkVaku9U/maxresdefault.jpg",
                viewCount = 234000,
                rating = 4.6f
            ),
            TutorialVideo(
                title = "Flexibility and Stretching",
                description = "Essential stretches to improve flexibility and reduce muscle tension",
                youtubeId = "L_xrDAtykMI",
                category = "Flexibility",
                duration = "12:30",
                thumbnailUrl = "https://img.youtube.com/vi/L_xrDAtykMI/maxresdefault.jpg",
                viewCount = 445000,
                rating = 4.5f
            ),
            TutorialVideo(
                title = "Core Strengthening Routine",
                description = "Build a strong core with this comprehensive workout routine",
                youtubeId = "Pah4jeLj0qM",
                category = "Strength",
                duration = "18:22",
                thumbnailUrl = "https://img.youtube.com/vi/Pah4jeLj0qM/maxresdefault.jpg",
                viewCount = 789000,
                rating = 4.7f
            ),
            TutorialVideo(
                title = "Morning Yoga Routine",
                description = "Start your day with this energizing 10-minute yoga flow",
                youtubeId = "VaoV1PrYft4",
                category = "Yoga",
                duration = "10:15",
                thumbnailUrl = "https://img.youtube.com/vi/VaoV1PrYft4/maxresdefault.jpg",
                viewCount = 1100000,
                rating = 4.9f
            ),
            TutorialVideo(
                title = "Full Body Stretching",
                description = "Complete stretching routine for all major muscle groups",
                youtubeId = "qULTwwWOUD8",
                category = "Flexibility",
                duration = "25:30",
                thumbnailUrl = "https://img.youtube.com/vi/qULTwwWOUD8/maxresdefault.jpg",
                viewCount = 356000,
                rating = 4.5f
            )
        )

        repository.insertTutorialVideos(tutorialVideos)
    }
}