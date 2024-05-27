package com.koniukhov.brushbliss.data

import android.graphics.Canvas
import android.graphics.Path

class CustomBrushTool(color: Int,
                      alpha: Int,
                      strokeWidth: Float,
    ): BrushTool(color, alpha, strokeWidth) {
    override fun draw(canvas: Canvas, path: Path) {
        canvas.drawPath(path, paint)
    }
}