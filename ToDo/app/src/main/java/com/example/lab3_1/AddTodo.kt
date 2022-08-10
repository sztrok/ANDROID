package com.example.lab3_1

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.lab3_1.databinding.ActivityAddTodoBinding
import java.util.*

class AddTodo: AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var binding: ActivityAddTodoBinding

    companion object{
        const val NOTI=0
        const val SCIENCE=1
        const val GAMES=2
        const val TRIP=3
        const val REPAIR=4
    }

    var pickedDay=-1
    var pickedYear=-1
    var pickedMonth=-1
    var pickedHour=-1
    var pickedMinute=-1

    var button=-1

    var day=0
    var month=0
    var year=0
    var hour=0
    var minute=0

    var dayString=""
    var monthString=""
    var hourString=""
    var minuteString=""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddTodoBinding.inflate(layoutInflater)
        val view= binding.root
        setContentView(view)

    }

    fun buttonClick(view: View){
        binding.addTodoName.clearFocus()
        if (button != -1){
            findViewById<ImageButton>(button).setColorFilter(Color.BLACK)
        }
        button = (view as ImageButton).id
        findViewById<ImageButton>(button).setColorFilter(Color.WHITE)
    }




    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        pickedDay=dayOfMonth
        pickedMonth=month
        pickedYear=year
        setDateString()

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        pickedHour=hourOfDay
        pickedMinute=minute
        setTimeString()
    }

    fun dateClick(view: View) {
        binding.addTodoName.clearFocus()
        updateDateTime()
        DatePickerDialog(this, this, year, month, day).show()
    }

    fun timeClick(view: View) {
        binding.addTodoName.clearFocus()
        updateDateTime()
        TimePickerDialog(this, this, hour, minute, true).show()
    }


    private fun updateDateTime(){
        val cal: Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun setTimeString(){
        minuteString=""
        hourString=""

        if(pickedMinute<10){
            minuteString+="0"
        }
        if(pickedHour<10){
            hourString+="0"
        }

        minuteString+=pickedMinute
        hourString+=pickedHour
        binding.buttonTime.text="$hourString:$minuteString"

    }

    private fun setDateString(){
        dayString=""
        monthString=""

        if(pickedDay<10){
            dayString+="0"

        }
        if(pickedMonth<10){
            monthString+="0"
        }

        dayString+=pickedDay
        monthString+=(pickedMonth+1)
        binding.buttonDate.text="$dayString/$monthString/$year"
    }

    fun doneClick(view:View){
        val name: String=binding.addTodoName.text.toString()
        val desc: String=binding.addTodoDesc.text.toString()
        if(name.isEmpty()){
            Toast.makeText(this,"Insert name",Toast.LENGTH_SHORT).show()
            return
        }

        var image=-1
        when(button){
            binding.buttonNoti.id -> image= NOTI
            binding.buttonScience.id -> image= SCIENCE
            binding.buttonGames.id -> image= GAMES
            binding.buttonTrip.id -> image= TRIP
            binding.buttonBuild.id -> image= REPAIR
        }

        if(image==-1){
            Toast.makeText(this,"Select icon",Toast.LENGTH_SHORT).show()
            return
        }
        if(day==-1){
            Toast.makeText(this,"Select date",Toast.LENGTH_SHORT).show()
            return
        }
        if(hour==-1){
            Toast.makeText(this,"Select time",Toast.LENGTH_SHORT).show()
            return
        }

        val intent= Intent()
        intent.putExtra("name", name)
        intent.putExtra("icon",image)
        intent.putExtra("date","$dayString/$monthString/$year")
        intent.putExtra("time","$hourString:$minuteString")
        intent.putExtra("desc",desc)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
}