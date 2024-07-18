package com.koniukhov.brushbliss.data

import android.graphics.Paint

abstract class BrushTool(
    private var color: Int,
    private var alpha: Int,
    private var strokeWidth: Float
    ) : DrawingTool() {

    protected val paint: Paint = Paint()

    init {
        updatePaint()
    }

    fun setColor(color: Int){
        this.color = color
    }

    fun setAlpha(alpha: Int){
        this.alpha = alpha
    }

    fun setStrokeWidth(strokeWidth: Float){
        this.strokeWidth = strokeWidth
    }

    fun updatePaint(){
        paint.apply {
            color = this@BrushTool.color
            alpha = this@BrushTool.alpha
            strokeWidth = this@BrushTool.strokeWidth
        }
    }
}