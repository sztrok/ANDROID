package com.example.lab3_1

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity :AppCompatActivity() {

    private lateinit var detailName: TextView
    private lateinit var detailDesc: TextView
    private lateinit var detailTime: TextView
    private lateinit var detailDate: TextView
    private lateinit var detailImage: ImageView
    var imageID: Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        detailName=findViewById(R.id.detail_name)
        detailDesc=findViewById(R.id.detail_desc)
        detailTime=findViewById(R.id.detail_time)
        detailDate=findViewById(R.id.detail_date)
        detailImage=findViewById(R.id.detail_icon)

        val bundle: Bundle? = intent.extras
        if(bundle!=null){
            detailName.text=bundle.getString("name")
            val desc=bundle.getString("desc")
            detailDesc.text="Description: $desc"
            detailDate.text=bundle.getString("date")
            detailTime.text=bundle.getString("time")
            imageID= bundle.getInt("image")

        }

        when(imageID){
            0-> detailImage.setImageResource(R.drawable.outline_notifications_black)
            1-> detailImage.setImageResource(R.drawable.outline_science_black)
            2-> detailImage.setImageResource(R.drawable.outline_sports_esports_black)
            3-> detailImage.setImageResource(R.drawable.outline_hiking_black)
            4-> detailImage.setImageResource(R.drawable.outline_construction_black)
        }


    }



}