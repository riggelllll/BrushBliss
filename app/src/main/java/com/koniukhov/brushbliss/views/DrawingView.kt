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
import com.koniukhov.brushbliss.data.CommonBrush
import com.koniukhov.brushbliss.data.UserSettingsManager
import com.koniukhov.brushbliss.data.UserSettingsManager.Companion.datastore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var drawPath: Path = Path()
    private var canvasPaint: Paint = Paint(Paint.DITHER_FLAG)
    private var canvasBitmap: Bitmap? = null
    private var drawCanvas: Canvas? = null

    private val userSettingsManager = UserSettingsManager(context.datastore)
    private val userSettingsFlow = userSettingsManager.userSettingsFlow

    private lateinit var brush: CommonBrush

    init {
        initBaseBrush()
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
        if (::brush.isInitialized){
            brush.draw(canvas, drawPath)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> drawPath.moveTo(touchX, touchY)
            MotionEvent.ACTION_MOVE -> drawPath.lineTo(touchX, touchY)
            MotionEvent.ACTION_UP -> {
                drawPath.lineTo(touchX, touchY)
                brush.draw(drawCanvas!!, drawPath)
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

    private fun initBaseBrush(){
        CoroutineScope(Dispatchers.IO).launch {
            userSettingsFlow.collect{
                brush = CommonBrush(
                    it.color,
                    it.alpha,
                    it.strokeWidth,
                    true,
                    Paint.Style.STROKE,
                    Paint.Join.ROUND,
                    Paint.Cap.ROUND,
                )
            }
        }
    }
}