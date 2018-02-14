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
import java.io.File

class ImageResizerSpec :Spek({
    describe("image risezer") {
        val testFileName = "/imageResizer/test.jpg"

        fun testResizer(resizer :ImageResizer) {
            assertNotSame(0, resizer.resize(100).size)
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