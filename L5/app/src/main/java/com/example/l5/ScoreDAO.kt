package com.example.l5

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoreDAO {
    @Query("SELECT * FROM score")
    fun getAll(): List<Score>

//    @Query("SELECT * FROM score WHERE gameID = :id")
//    fun getWithID(id: Int): List<Score>

    @Insert
    fun insertAll(vararg score: Score)
}