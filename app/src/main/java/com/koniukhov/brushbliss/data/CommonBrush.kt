package com.koniukhov.brushbliss.data

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path

class CommonBrush(
    color: Int,
    alpha: Int,
    strokeWidth: Float,
    private var isAntiAlias: Boolean,
    private var style: Paint.Style,
    private var strokeJoin: Paint.Join,
    private var strokeCap: Paint.Cap,
    ) : BrushTool(color, alpha, strokeWidth) {
    init {
        paint.apply {
            isAntiAlias = this@CommonBrush.isAntiAlias
            style = this@CommonBrush.style
            strokeJoin = this@CommonBrush.strokeJoin
            strokeCap = this@CommonBrush.strokeCap
        }
    }

    override fun draw(canvas: Canvas, path: Path) {
        canvas.drawPath(path, paint)
    }
}