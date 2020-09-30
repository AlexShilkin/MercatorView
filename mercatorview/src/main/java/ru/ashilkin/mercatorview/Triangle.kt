package ru.ashilkin.mercatorview

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

class Triangle {

    var vertexBuffer: FloatBuffer
    var indexBuffer: ByteBuffer

    private val vertices = floatArrayOf( // Vertices of the triangle
        0.0f, 1.0f, 0.0f,    // 0. top
        -1.0f, -1.0f, 0.0f,  // 1. left-bottom
        1.0f, -1.0f, 0.0f    // 2. right-bottom
    )
    private val indices = byteArrayOf(0, 1, 2) // Indices to above vertices (in CCW)

    init {
        val vbb = ByteBuffer.allocateDirect(vertices.size * 4)
        vbb.order(ByteOrder.nativeOrder())
        vertexBuffer = vbb.asFloatBuffer()
        vertexBuffer.put(vertices)
        vertexBuffer.position(0)

        indexBuffer = ByteBuffer.allocateDirect(indices.size)
        indexBuffer.put(indices)
        indexBuffer.position(0)
    }

    fun draw(gl: GL10) {
        gl.apply {
            // Enable vertex-array and define the buffers
            glEnableClientState(GL10.GL_VERTEX_ARRAY)
            glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)

            // Draw the primitives via index-array
            glDrawElements(GL10.GL_TRIANGLES, indices.size, GL10.GL_UNSIGNED_BYTE, indexBuffer)
            glDisableClientState(GL10.GL_VERTEX_ARRAY)
        }
    }



}