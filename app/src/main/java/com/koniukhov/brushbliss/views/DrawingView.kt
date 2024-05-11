package com.koniukhov.brushbliss.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var drawPath: Path = Path()
    private var canvasPaint: Paint = Paint(Paint.DITHER_FLAG)
    private var drawPaint: Paint = Paint()
    private var canvasBitmap: Bitmap? = null
    private var drawCanvas: Canvas? = null

    init {
        setup()
    }

    private fun setup() {
        drawPaint.color = Color.BLACK
        drawPaint.isAntiAlias = true
        drawPaint.strokeWidth = 8f
        drawPaint.style = Paint.Style.STROKE
        drawPaint.strokeJoin = Paint.Join.ROUND
        drawPaint.strokeCap = Paint.Cap.ROUND
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvasBitmap?.let {
            drawCanvas = Canvas(it)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(canvasBitmap!!, 0f, 0f, canvasPaint)
        canvas.drawPath(drawPath, drawPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> drawPath.moveTo(touchX, touchY)
            MotionEvent.ACTION_MOVE -> drawPath.lineTo(touchX, touchY)
            MotionEvent.ACTION_UP -> {
                drawPath.lineTo(touchX, touchY)
                drawCanvas?.drawPath(drawPath, drawPaint)
                drawPath.reset()
            }
            else -> return false
        }
        invalidate()
        return true
    }

    fun clearCanvas() {
        drawCanvas?.drawColor(Color.WHITE)
        invalidate()
    }
}