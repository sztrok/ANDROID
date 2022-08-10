package com.example.l5

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(Score::class)],version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun scoreDAO(): ScoreDAO
}