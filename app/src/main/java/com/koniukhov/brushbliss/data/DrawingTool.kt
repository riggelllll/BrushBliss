package com.koniukhov.brushbliss.data

import android.graphics.Canvas
import android.graphics.Path

abstract class DrawingTool {
    abstract fun draw(canvas: Canvas, path: Path)
}