package com.example.simlechatv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simlechatv2.databinding.ActivityChatBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var currentUser: String
    private var list: ArrayList<Message> = arrayListOf()

    private val database= Firebase.database
    private val dbRef= database.getReference("messages")



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_sign_out){
            AuthUI.getInstance().signOut(this).addOnCompleteListener {
                Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatBinding.inflate(layoutInflater)
        val view= binding.root
        setContentView(view)
        val bundle: Bundle?= intent.extras
        currentUser= bundle?.getString("user").toString()

        binding.messageRecyclerView.layoutManager= LinearLayoutManager(this)
        binding.messageRecyclerView.adapter= MessageAdapter(list)



        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val messageList: ArrayList<Message> = ArrayList()
                for (ss in snapshot.children){
                    var messageAuthor= ss.child("messageAuthor").value as String?
                    if(messageAuthor==currentUser){
                        messageAuthor="You"
                    }
                    val messageText= ss.child("messageText").value as String?
                    if(messageAuthor!=null && messageText!=null){
                        val message= Message(
                            user = messageAuthor,
                            message = messageText
                        )
                        messageList.add(message
                        )
                    }

                }

                list.clear()
                list.addAll(messageList)


            }

            override fun onCancelled(error: DatabaseError) {
                val intent= Intent(applicationContext,MainActivity::class.java)
                startActivity(intent)
            }


        })



    }

    fun onClick(view: View){
        val text= binding.editText.text.toString()
        val message= Message(
                user = currentUser,
                message = text
        )
        val key= dbRef.push().key
        if(key!=null){
            dbRef.child(key).child("messageAuthor").setValue(message.user)
            dbRef.child(key).child("messageText").setValue(message.message)
        }

        binding.editText.clearFocus()
        binding.editText.text.clear()
    }



















}