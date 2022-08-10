package com.example.l5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScoreAdapter(private val scoreList: List<Score>):
        RecyclerView.Adapter<ScoreAdapter.ViewHolder>(){

            class ViewHolder(view: View): RecyclerView.ViewHolder(view){
                val player1Score: TextView= view.findViewById(R.id.p1_score)
                val player2Score: TextView= view.findViewById(R.id.p2_score)
                val gameId: TextView= view.findViewById(R.id.game_id)

            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.score_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.gameId.text=scoreList[position].gameID.toString()
        holder.player1Score.text=scoreList[position].player1Score.toString()
        holder.player2Score.text=scoreList[position].player2Score.toString()
    }

    override fun getItemCount(): Int {
        return scoreList.size
    }
}