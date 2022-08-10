package com.example.l5

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


class GameView(context: Context, attributeSet: AttributeSet):
    SurfaceView(context, attributeSet), SurfaceHolder.Callback
{
    private val thread: GameThread
    private val fps=40
    private lateinit var ball: Ball
    private lateinit var paddleDown: Paddle
    private lateinit var paddleUp: Paddle
    private lateinit var database: AppDatabase
    var playerUpScore=0
    var playerDownScore=0

    init{
        holder.addCallback(this)
        thread= GameThread(holder,this)



    }

    override fun surfaceCreated(holder: SurfaceHolder) {


        thread.start()
        thread.running=true
        paddleDown= Paddle(width*1f, height*1f,"down")
        paddleUp= Paddle(width*1f,height*1f,"up")
        ball= Ball(width*1f, height*1f)
        ball.reset(width/2,height/2)

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.running=false
        thread.join()
        GlobalScope.launch {
            try{
                database= Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"Scores.db").build()
                database.scoreDAO().insertAll(Score(0,playerUpScore,playerDownScore))

            }
            catch (e: Exception){
                println("NIE DZIALA")
                Log.d("DLACZEGO",e.message.toString())
            }


        }


//        Toast.makeText(context, "zepsute", Toast.LENGTH_SHORT).show()



    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        if(canvas==null) return
        val magenta= Paint().apply{
            color= Color.MAGENTA
            style= Paint.Style.STROKE
            strokeWidth=8f
            isAntiAlias=true
        }


        val red= Paint().apply{
            color= Color.RED
            textSize=40f
            style=Paint.Style.FILL
            isAntiAlias=true
        }
        val cyan= Paint().apply {
            color=Color.CYAN
            style= Paint.Style.STROKE
            strokeWidth=8f
            isAntiAlias=true


        }

        val green= Paint().apply {
            color=Color.GREEN
            style= Paint.Style.STROKE
            strokeWidth=8f
            textSize=60f
            isAntiAlias=false
            textAlign=Paint.Align.CENTER

        }

        val gray= Paint().apply {
            color=Color.GRAY
            style= Paint.Style.STROKE
            strokeWidth=8f
        }



        canvas.drawLine(0f,height/2f,width*1f,height/2f,gray)
        canvas.drawRect(paddleDown.getRect(),magenta)
        canvas.drawRect(paddleUp.getRect(),cyan)

        canvas.drawText(playerUpScore.toString(),70f,height/2f-25f,green)
        canvas.drawText(playerDownScore.toString(),width*1f-70f,height/2f+55f,green)
        canvas.drawRect(ball.getRect(),red)




    }

    fun update(){
        paddleDown.update(fps)
        paddleUp.update(fps)
        ball.update(fps)

        if(ball.givePointDown){
            playerDownScore++
            ball.givePointDown=false
        }

        if(ball.givePointUp){
            playerUpScore++
            ball.givePointUp=false
        }


        if(RectF.intersects(paddleDown.getRect(),ball.getRect()) ||RectF.intersects(paddleUp.getRect(),ball.getRect()) ){
            ball.reverseYVelocity()
            ball.setRandomVelocity()


        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when(event.action and MotionEvent.ACTION_MASK){

                MotionEvent.ACTION_DOWN-> {
                    if(event.y <height/2){
                        if(event.x >width/2){

                            paddleUp.setMovementState(paddleUp.RIGHT)
                        }
                        else{

                            paddleUp.setMovementState(paddleUp.LEFT)
                        }
                    }
                    else{
                        if(event.x >width/2){
                            paddleDown.setMovementState(paddleDown.RIGHT)
                        }
                        else{
                            paddleDown.setMovementState(paddleDown.LEFT)
                        }
                    }
                }

                MotionEvent.ACTION_UP-> {
                    paddleDown.setMovementState(paddleDown.STOPPED)
                    paddleUp.setMovementState(paddleUp.STOPPED)
                }
            }
        }

        return true
    }
}