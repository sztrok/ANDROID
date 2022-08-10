package com.example.simlechatv2

import java.sql.Time
import java.util.*

class ChatMessage(private var messageText: String = "a", private var messageUser: String = "b") {

    private var messageTime= System.currentTimeMillis()

    init {

    }





    fun getMessageUser(): String {
        return messageUser
    }

    fun getMessageText(): String {
        return messageText
    }

    fun getMessageTime(): Long {
        return messageTime
    }

    fun setMessageUser(user: String){
        this.messageUser=user
    }

    fun setMessageText(text: String){
        this.messageText=text
    }

    fun setMessageTime(time: Long){
        this.messageTime=time
    }



}