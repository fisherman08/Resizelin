package com.kyproj.resizelin

/**
 * Created on 2018/02/02.
 */
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import java.io.ByteArrayInputStream
import java.io.File
import javax.imageio.ImageIO

class ImageResizerSpec :Spek({
    describe("image resizer") {
        val testFileName = "/imageResizer/test.jpg"

        fun testResizer(resizer :ImageResizer) {
            val resized = resizer.resize(width = 100)
            assertEquals(100, ImageIO.read(ByteArrayInputStream(resized)).width)
            assertNotSame(0, resized.size)
        }

        on("initialize with byte array") {
            val fileByteArray = this.javaClass.getResource(testFileName).readBytes()
            val resizer = ImageResizer(fileByteArray)
            it("can initialize"){
                testResizer(resizer)
            }
        }

        on("initialize with file object") {
            val file : File = File(this.javaClass.getResource(testFileName).toURI())
            val resizer = ImageResizer(file)
            it("can initialize"){
                testResizer(resizer)
            }
        }

        on("initialize with filename"){
            val fileName = this.javaClass.getResource(testFileName).toURI().path
            val resizer = ImageResizer(fileName)
            it("can initialize"){
                testResizer(resizer)
            }
        }

    }
})