package com.example.simlechatv2

import android.graphics.Color
import android.graphics.text.LineBreaker
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(private val messagesList: ArrayList<Message>): RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val messageAuthor: TextView= view.findViewById(R.id.message_author)
        val messageText: TextView= view.findViewById(R.id.message_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.message,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user= messagesList[position].user.split("@")
        holder.messageAuthor.text=user[0]
        holder.messageText.text=messagesList[position].message
//        holder.messageText.breakStrategy= LineBreaker.BREAK_STRATEGY_SIMPLE
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

}