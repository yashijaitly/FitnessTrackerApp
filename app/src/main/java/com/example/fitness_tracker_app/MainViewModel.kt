package com.example.fitness_tracker_app
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.fitness_tracker_app.FitTrackerRepository
import com.example.fitness_tracker_app.data.database.entities.*

class MainViewModel (private val repository: FitTrackerRepository):ViewModel(){
    // Current user
    val currentUser: LiveData<User?> = repository.getCurrentUser()

    // User stats
    private val _userStats = MutableLiveData<UserStats>()
    val userStats: LiveData<UserStats> = _userStats

    // Active workout
    private val _activeWorkout = MutableLiveData<Workout?>()
    val activeWorkout: LiveData<Workout?> = _activeWorkout

    // Loading states
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Error messages
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    data class UserStats(
        val totalWorkouts: Int = 0,
        val totalCalories: Float = 0f,
        val totalMinutes: Int = 0
    )

    fun loadUserStats(userId: Long) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val workouts = repository.getTotalWorkouts(userId)
                val calories = repository.getTotalCalories(userId) ?: 0f
                val minutes = repository.getTotalMinutes(userId) ?: 0

                _userStats.value = UserStats(workouts, calories, minutes)
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load user stats: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun loadActiveWorkout(userId: Long) {
        viewModelScope.launch {
            try {
                val workout = repository.getActiveWorkout(userId)
                _activeWorkout.value = workout
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load active workout: ${e.message}"
            }
        }
    }

    fun createUser(username: String, email: String, firstName: String?, lastName: String?) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val user = User(
                    username = username,
                    email = email,
                    firstName = firstName,
                    lastName = lastName
                )
                repository.insertUser(user)
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Failed to create user: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.updateUser(user)
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update user: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}

class MainViewModelFactory(private val repository: FitTrackerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
