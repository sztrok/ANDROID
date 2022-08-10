package com.example.l5

import android.graphics.RectF

class Paddle(screenX: Float , screenY: Float,type: String){
    private var rectF: RectF
    private var length=0
    private var height=0
    private var paddleSpeed=0
    private var x=0f
    private var y=0f
    val STOPPED=0
    val LEFT=1
    val RIGHT=2
    private var paddleMoving= STOPPED
    private var screenX=0f
    private var screenY=0f

//l t r b

    init {
        this.screenX=screenX
        this.screenY=screenY
        length=250
        height=50
        x=screenX/2 -length/2
        when(type){
            "down"-> {
                y=screenY-200f
            }
            "up"->{
                y=200f
            }
        }

        paddleSpeed=700
        rectF= RectF(x,y,x+length,y+height)
    }

    fun getRect(): RectF{
        return rectF
    }

    fun setMovementState(state: Int){
        paddleMoving=state
    }

    fun update(fps: Int){
        if(paddleMoving==LEFT && rectF.left>0){
            x -= paddleSpeed / fps
        }

        if(paddleMoving==RIGHT && rectF.right<screenX){
            x += paddleSpeed/fps
        }
        rectF.left=x
        rectF.right=x+length


    }

}