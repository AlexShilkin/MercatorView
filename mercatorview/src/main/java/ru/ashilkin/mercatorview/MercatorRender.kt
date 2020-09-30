package ru.ashilkin.mercatorview

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class MercatorRender : GLSurfaceView.Renderer {

    private val TAG = "MercatorRender"

    private var mProgramObject: Int = 0

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    //test triangle
    private val mVerticesData =
        floatArrayOf(0.0f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f)
    private lateinit var mVertices: FloatBuffer


    init {
        mVertices = ByteBuffer.allocateDirect(mVerticesData.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mVertices.put(mVerticesData).position(0)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onDrawFrame(gl: GL10?) {
        //set the viewport
        GLES30.glViewport(0, 0, mWidth, mHeight)

        //clear the color buffer
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)

        //use the program object
        GLES30.glUseProgram(mProgramObject)

        //load the vertex data
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, mVertices)
        GLES30.glEnableVertexAttribArray(0)

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        this.mWidth = width
        this.mHeight = height
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        val vShaderStr =
            "#version 300 es 			  \n" +
                    "layout(location = 0) in vec4 vPosition;           \n" +
                    "void main()                  \n" +
                    "{                            \n" +
                    "   gl_Position = vPosition;  \n" +
                    "}                            \n"

        val fShaderStr =
            "#version 300 es		 			          	\n" +
                    "precision mediump float;					  	\n" +
                    "out vec4 fragColor;	 			 		  	\n" +
                    "void main()                                  \n" +
                    "{                                            \n" +
                    "  fragColor = vec4 ( 1.0, 0.0, 0.0, 1.0 );	\n" +
                    "}                                            \n"

        val vertexShader: Int
        val fragmentShader: Int
        val programObject: Int
        val linked = IntArray(1)

        //load the vertex/fragment shaders
        vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vShaderStr)
        fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fShaderStr)

        //create the program object
        programObject = GLES30.glCreateProgram()

        if (programObject == 0) {
            return
        }

        GLES30.glAttachShader(programObject, vertexShader)
        GLES30.glAttachShader(programObject, fragmentShader)

        //bind vPosition to attribute 0
        GLES30.glBindAttribLocation(programObject, 0, "vPosition")

        //link the program
        GLES30.glLinkProgram(programObject)

        //check the link status
        GLES30.glGetProgramiv(programObject, GLES30.GL_LINK_STATUS, linked, 0)

        if (linked[0] == 0) {
            Log.e(TAG, "Error linking program:")
            Log.e(TAG, GLES30.glGetProgramInfoLog(programObject))
            GLES30.glDeleteProgram(programObject)
            return
        }

        mProgramObject = programObject

        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
    }

    /**
     * Create a shader object, load the shader source, and compile the shader
     */
    private fun loadShader(type: Int, shaderSrc: String): Int {
        val shader: Int
        val compiled = IntArray(1)

        //create shader object
        shader = GLES30.glCreateShader(type)
        if (shader == 0) {
            return 0
        }

        //load the shader object
        GLES30.glShaderSource(shader, shaderSrc)

        //compile the shader
        GLES30.glCompileShader(shader)

        //check the compile status
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0)
        if (compiled[0] == 0) {
            Log.e(TAG, GLES30.glGetShaderInfoLog(shader))
            GLES30.glDeleteShader(shader)
            return 0
        }

        return shader
    }
}