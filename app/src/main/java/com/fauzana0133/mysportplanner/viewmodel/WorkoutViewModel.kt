package com.fauzana0133.mysportplanner.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fauzana0133.mysportplanner.database.WorkoutDao
import com.fauzana0133.mysportplanner.model.Workout
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WorkoutViewModel(private val dao: WorkoutDao) : ViewModel() {
    val workoutList = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insert(workout: Workout) {
        viewModelScope.launch { dao.insert(workout) }
    }

    fun delete(workout: Workout) {
        viewModelScope.launch { dao.delete(workout) }
    }

    fun update(workout: Workout) {
        viewModelScope.launch { dao.update(workout) }
    }
}

class WorkoutViewModelFactory(private val dao: WorkoutDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkoutViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
