package com.kyproj.resizelin

/**
 * Created on 2018/02/02.
 */
import junit.framework.Assert.*
import org.junit.Test
import java.io.File


class ImageResizerTestCase {
    private val testFileName = "/imageResizer/test.jpg"

    @Test
    fun testInitializerByteArray(){
        val fileByteArray = this.javaClass.getResource(testFileName).readBytes()
        val resizer = ImageResizer(fileByteArray)
        testResizer(resizer)
    }

    @Test
    fun testInitializerFileObject(){
        val file : File = File(this.javaClass.getResource(testFileName).toURI())
        val resizer = ImageResizer(file)
        testResizer(resizer)
    }

    @Test
    fun testInitializerFileName(){
        val fileName = this.javaClass.getResource(testFileName).toURI().path
        val resizer = ImageResizer(fileName)
        testResizer(resizer)
    }

    private fun testResizer(resizer :ImageResizer) {
        assertNotSame(0, resizer.resize(100).size)
    }
}