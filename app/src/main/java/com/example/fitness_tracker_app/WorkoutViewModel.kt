package com.example.fitness_tracker_app
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.Job
import com.example.fitness_tracker_app.FitTrackerRepository
import com.example.fitness_tracker_app.data.database.entities.*
class WorkoutViewModel(private val repository: FitTrackerRepository) : ViewModel() {

    // Workout data
    private val _currentWorkout = MutableLiveData<Workout?>()
    val currentWorkout: LiveData<Workout?> = _currentWorkout

    private val _workoutExercises = MutableLiveData<List<WorkoutExercise>>()
    val workoutExercises: LiveData<List<WorkoutExercise>> = _workoutExercises

    // Timer data
    private val _timerSeconds = MutableLiveData<Int>(0)
    val timerSeconds: LiveData<Int> = _timerSeconds

    private val _isTimerRunning = MutableLiveData<Boolean>(false)
    val isTimerRunning: LiveData<Boolean> = _isTimerRunning

    // Exercise selection
    val allExercises: LiveData<List<Exercise>> = repository.getAllExercises()

    private val _selectedExercise = MutableLiveData<Exercise?>()
    val selectedExercise: LiveData<Exercise?> = _selectedExercise

    // Loading and error states
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private var timerJob: Job? = null

    fun startWorkout(userId: Long) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val workout = Workout(
                    userId = userId,
                    startTime = System.currentTimeMillis(),
                    status = "active"
                )
                val workoutId = repository.insertWorkout(workout)
                val savedWorkout = workout.copy(id = workoutId)
                _currentWorkout.value = savedWorkout

                startTimer()
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Failed to start workout: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun pauseWorkout() {
        pauseTimer()
        _currentWorkout.value?.let { workout ->
            val updatedWorkout = workout.copy(status = "paused")
            _currentWorkout.value = updatedWorkout
            updateWorkoutInDatabase(updatedWorkout)
        }
    }

    fun resumeWorkout() {
        startTimer()
        _currentWorkout.value?.let { workout ->
            val updatedWorkout = workout.copy(status = "active")
            _currentWorkout.value = updatedWorkout
            updateWorkoutInDatabase(updatedWorkout)
        }
    }

    fun completeWorkout() {
        pauseTimer()
        _currentWorkout.value?.let { workout ->
            val endTime = System.currentTimeMillis()
            val duration = ((endTime - workout.startTime) / 60000).toInt() // Convert to minutes
            val totalCalories = calculateTotalCalories()

            val completedWorkout = workout.copy(
                endTime = endTime,
                duration = duration,
                totalCalories = totalCalories,
                status = "completed"
            )
            _currentWorkout.value = completedWorkout
            updateWorkoutInDatabase(completedWorkout)
        }
    }

    fun addExerciseToWorkout(exercise: Exercise, durationMinutes: Int) {
        _currentWorkout.value?.let { workout ->
            viewModelScope.launch {
                try {
                    val calories = calculateCalories(exercise.metValue, durationMinutes, 70f) // Assuming 70kg body weight
                    val workoutExercise = WorkoutExercise(
                        workoutId = workout.id,
                        exerciseId = exercise.id,
                        duration = durationMinutes,
                        calories = calories
                    )
                    repository.insertWorkoutExercise(workoutExercise)
                    loadWorkoutExercises(workout.id)
                } catch (e: Exception) {
                    _errorMessage.value = "Failed to add exercise: ${e.message}"
                }
            }
        }
    }

    fun selectExercise(exercise: Exercise) {
        _selectedExercise.value = exercise
    }

    fun loadActiveWorkout(userId: Long) {
        viewModelScope.launch {
            try {
                val workout = repository.getActiveWorkout(userId)
                _currentWorkout.value = workout
                workout?.let {
                    loadWorkoutExercises(it.id)
                    if (it.status == "active") {
                        // Calculate elapsed time and continue timer
                        val elapsedSeconds = ((System.currentTimeMillis() - it.startTime) / 1000).toInt()
                        _timerSeconds.value = elapsedSeconds
                        startTimer()
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load workout: ${e.message}"
            }
        }
    }

    private fun loadWorkoutExercises(workoutId: Long) {
        repository.getWorkoutExercises(workoutId).observeForever { exercises ->
            _workoutExercises.value = exercises
        }
    }

    private fun startTimer() {
        if (_isTimerRunning.value == true) return

        _isTimerRunning.value = true
        timerJob = viewModelScope.launch {
            while (_isTimerRunning.value == true) {
                delay(1000)
                _timerSeconds.value = (_timerSeconds.value ?: 0) + 1
            }
        }
    }

    private fun pauseTimer() {
        _isTimerRunning.value = false
        timerJob?.cancel()
    }

    private fun updateWorkoutInDatabase(workout: Workout) {
        viewModelScope.launch {
            try {
                repository.updateWorkout(workout)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update workout: ${e.message}"
            }
        }
    }

    private fun calculateTotalCalories(): Float {
        return _workoutExercises.value?.sumOf { it.calories?.toDouble() ?: 0.0 }?.toFloat() ?: 0f
    }

    private fun calculateCalories(metValue: Float, durationMinutes: Int, weightKg: Float): Float {
        // Calories = MET × weight in kg × time in hours
        return metValue * weightKg * (durationMinutes / 60f)
    }

    fun formatTime(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return if (hours > 0) {
            String.format("%02d:%02d:%02d", hours, minutes, secs)
        } else {
            String.format("%02d:%02d", minutes, secs)
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

class WorkoutViewModelFactory(private val repository: FitTrackerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkoutViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
