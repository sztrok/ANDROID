package com.example.l5

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score")
data class Score(
        @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val gameID: Int,
        val player1Score: Int,
        val player2Score: Int
)
