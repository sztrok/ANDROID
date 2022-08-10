package com.example.l5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var database: AppDatabase
    private var index : Int=0
    private var p1Points: Int= 0
    private var p2Points: Int=0
    private lateinit var scoreList: List<Score>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        GlobalScope.launch {
//            try{
//                database= Room.databaseBuilder(applicationContext,AppDatabase::class.java,"Scores.db").build()
//                val score= Score(1,2,5)
//                database.scoreDAO().insertAll(score)
//            }
//            catch (e: Exception){
//                println("NIE DZIALA")
//                Log.d("DLACZEGO",e.message.toString())
//            }
//
//
//        }

        GlobalScope.launch {
            try{
                database=Room.databaseBuilder(applicationContext,AppDatabase::class.java,"Scores.db").build()
            }
            catch (e: Exception){
                println("MAIN NIE DZIALA")
            }

            scoreList= database.scoreDAO().getAll()
            println("BAZA:")
            println(scoreList)
        }

        val bundle: Bundle?=intent.extras
        if (bundle!=null){
            val score= Score(index,p1Points,p2Points)
            database.scoreDAO().insertAll(score)
        }



    }

    fun buttonClick(view: View){
        val intent= Intent(this,GameActivity::class.java)
        startActivity(intent)
    }

    fun prevScores(view: View){
        val intent= Intent(this,ScoreActivity::class.java)
        startActivity(intent)
    }


}