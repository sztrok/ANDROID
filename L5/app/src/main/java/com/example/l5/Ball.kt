package com.example.l5

import android.graphics.RectF
import java.util.*

class Ball(private val screenX: Float, private val screenY: Float) {
    private  var rectF: RectF
    private var xVelocity=0f
    private var yVelocity=0f
    private var ballWidth=40f
    private var ballHeight=40f
    var givePointUp=false
    var givePointDown=false

    init {
        xVelocity=200f
        yVelocity=500f
        rectF= RectF()
    }

    fun getRect(): RectF{
        return rectF
    }

    fun update(fps: Int){
        rectF.left+=xVelocity/fps
        rectF.top+= yVelocity/fps
        rectF.right=rectF.left+ ballWidth
        rectF.bottom= rectF.top- ballHeight

        if(rectF.left<=0 || rectF.right>=screenX){
            reverseXVelocity()
        }

        if(rectF.top<=0){
            givePointDown=true
            reset((screenX/2).toInt(),(screenY/2).toInt())
        }
        if(rectF.bottom>=screenY){
            givePointUp=true
            reset((screenX/2).toInt(),(screenY/2).toInt())
        }


    }


    fun reverseYVelocity(){
        yVelocity=-yVelocity
    }

    fun reverseXVelocity(){
        xVelocity=-xVelocity
    }

    fun setRandomYVelovity(){
        val generator= Random()
        val answer= Random().nextInt(2)
        if(answer==0){
            reverseYVelocity()
        }
    }

    fun setRandomVelocity(){
        val generator= Random()
        val answer= Random().nextInt(2)
        if(answer==0){
            reverseXVelocity()
        }

    }

    fun clearObstacleY(y: Float){
        rectF.bottom=y
        rectF.top=y-ballHeight
    }

    fun clearObstacleX(x: Float){
        rectF.left=x
        rectF.right=x+ ballWidth
    }

    fun reset(x: Int, y:Int){
        rectF.left=x/2f
        rectF.top= y-20f
        rectF.right=x/2f +ballWidth
        rectF.bottom=y- 20f -ballHeight
        setRandomVelocity()
        setRandomYVelovity()

    }

}