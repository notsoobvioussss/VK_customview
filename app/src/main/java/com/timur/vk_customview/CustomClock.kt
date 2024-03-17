package com.timur.vk_customview

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class CustomClock(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private var handler: Handler? = null

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.textSize = 50f
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startClock()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopClock()
    }

    private fun startClock() {
        val runnable = object : Runnable {
            override fun run() {
                invalidate()
                handler?.postDelayed(this, 1000)
            }
        }
        handler = Handler(Looper.getMainLooper())
        handler?.post(runnable)
    }

    private fun stopClock() {
        handler?.removeCallbacksAndMessages(null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = min(width, height) / 2f

        paint.strokeWidth = width * 0.05f
        canvas.drawCircle(centerX, centerY, radius - width * 0.025f, paint)

        paint.strokeWidth = width * 0.01f
        for (i in 0 until 60) {
            val angle = Math.PI / 30 * i
            val stopX = centerX + (radius * 0.85f * sin(angle)).toFloat()
            val stopY = centerY - (radius * 0.85f * cos(angle)).toFloat()
            if (i % 5 == 0) {
                canvas.drawCircle(stopX, stopY, width * 0.01f, paint)
            } else {
                canvas.drawCircle(stopX, stopY, width * 0.005f, paint)
            }
        }

        paint.textSize = width * 0.09f
        for (i in 1..12) {
            val angle = Math.PI / 6 * (i - 3)
            val x = centerX + cos(angle) * radius * 0.7
            val y = centerY + sin(angle) * radius * 0.7
            canvas.drawText(i.toString(), x.toFloat(), y.toFloat() + paint.textSize / 3, paint)
        }

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY) % 12
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        val totalSecondsInMinute = 60
        val totalMinutesInClock = 60
        val degreesPerMinute = 360f / totalMinutesInClock
        val degreesPerSecond = degreesPerMinute / totalSecondsInMinute
        val minuteAngle = minute * degreesPerMinute + second * degreesPerSecond
        drawHand(canvas, centerX, centerY, hour * 30 + minute / 2f, radius * 0.5f, width * 0.032f)
        drawHand(canvas, centerX, centerY, minuteAngle, radius * 0.8f, width * 0.016f)
        drawHand(canvas, centerX, centerY, second * 6f, radius * 0.9f, width * 0.01f)
    }

    private fun drawHand(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        angle: Float,
        length: Float,
        strokeWidth: Float
    ) {
        val radians = Math.toRadians(angle.toDouble())
        val endX = centerX + (length * sin(radians)).toFloat()
        val endY = centerY - (length * cos(radians)).toFloat()
        paint.strokeWidth = strokeWidth
        canvas.drawLine(centerX, centerY, endX, endY, paint)
        val endX1 = centerX - (length * 0.2f * sin(radians)).toFloat()
        val endY1 = centerY + (length * 0.2f * cos(radians)).toFloat()
        canvas.drawLine(centerX, centerY, endX1, endY1, paint)
    }
}
