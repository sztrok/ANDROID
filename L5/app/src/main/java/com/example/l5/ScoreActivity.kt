package com.example.l5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.l5.databinding.ActivityScoreBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class ScoreActivity: AppCompatActivity() {
    private lateinit var binding: ActivityScoreBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var scoreList: List<Score>
    private lateinit var database: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityScoreBinding.inflate(layoutInflater)
        val view= binding.root
        setContentView(view)
        recyclerView=findViewById(R.id.recycler_view)
        binding.recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)


        GlobalScope.launch {
            try{
                database= Room.databaseBuilder(applicationContext,AppDatabase::class.java,"Scores.db").build()
                scoreList=database.scoreDAO().getAll()
                binding.recyclerView.adapter=ScoreAdapter(scoreList)
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            catch (e: Exception){
                println("SCORE_ACTIV")
            }
        }

    }
}