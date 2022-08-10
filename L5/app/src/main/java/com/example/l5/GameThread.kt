package com.example.l5

import android.graphics.Canvas
import android.view.SurfaceHolder
import kotlin.concurrent.thread

class GameThread(private val surfaceHolder: SurfaceHolder, private val gameView: GameView):
    Thread() {
    private var canvas: Canvas?=null
    var running= false
    private var targetFPS= 30

    override fun run() {
        var startTime: Long
        var timeMillis: Long
        var waitTime: Long
        val targetTime=(1000/targetFPS).toLong()
        sleep(400)
        while(running){
            startTime=System.nanoTime()
            canvas= surfaceHolder.lockCanvas()
            gameView.draw(canvas)
            gameView.update()
            surfaceHolder.unlockCanvasAndPost(canvas)
            timeMillis=(System.nanoTime() - startTime)/1000000
            waitTime= targetTime-timeMillis
            if(waitTime>=0)
                sleep(waitTime)

//            else sleep(5)
        }

    }
}