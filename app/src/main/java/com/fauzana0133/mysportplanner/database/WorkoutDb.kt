package com.fauzana0133.mysportplanner.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fauzana0133.mysportplanner.model.Workout

@Database(entities = [Workout::class], version = 1, exportSchema = false)
abstract class WorkoutDb : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao

    companion object {
        @Volatile
        private var INSTANCE: WorkoutDb? = null

        fun getDatabase(context: Context): WorkoutDb {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutDb::class.java,
                    "workout_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
