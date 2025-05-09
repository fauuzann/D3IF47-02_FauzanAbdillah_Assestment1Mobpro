package com.fauzana0133.mysportplanner.database

import androidx.room.*
import com.fauzana0133.mysportplanner.model.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout ORDER BY id DESC")
    fun getAll(): Flow<List<Workout>>

    @Insert
    suspend fun insert(workout: Workout)

    @Update
    suspend fun update(workout: Workout)

    @Delete
    suspend fun delete(workout: Workout)

    @Query("SELECT * FROM workout WHERE id = :id")
    suspend fun getById(id: Int): Workout?
}
