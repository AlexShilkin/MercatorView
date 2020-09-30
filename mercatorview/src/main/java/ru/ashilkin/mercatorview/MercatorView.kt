package ru.ashilkin.mercatorview

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import android.util.AttributeSet

class MercatorView(
    context: Context?, attributeSet: AttributeSet?
) : GLSurfaceView(context, attributeSet) {

    private val CONTEXT_CLIENT_VERSION = 3

    var render: MercatorRender = MercatorRender()

    init {
        setEGLContextClientVersion(CONTEXT_CLIENT_VERSION)
        setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        setRenderer(render)
        renderMode = RENDERMODE_WHEN_DIRTY
        getHolder().setFormat(PixelFormat.RGBA_8888)
    }



}