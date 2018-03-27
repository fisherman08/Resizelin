package com.kyproj.resizelin

/**
 * Created on 2018/02/02.
 */
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.roundToInt


class ImageResizer {
    private val imageFile: ByteArray

    constructor(image: ByteArray) {
        imageFile = image
    }

    @Throws(Exception::class)
    constructor(filename: String) {
        imageFile = File(filename).readBytes()
    }

    @Throws(Exception::class)
    constructor(file: File) {
        imageFile = file.readBytes()
    }

    @Throws(Exception::class)
    fun resize(width: Int): ByteArray {

        // オリジナルのファイルを読み込む
        val byteArrayStream = ByteArrayInputStream(imageFile)
        val input = ImageIO.createImageInputStream(byteArrayStream)
        val readers = ImageIO.getImageReaders(input)

        try {

            if(!readers.hasNext()) {
                return ByteArray(0)
            }

            val reader = readers.next()
            reader.input = input
            val original = reader.read(0) ?: throw InvalidFileException("File is invalid")
            val originalWidth  = original.width.toDouble()
            val originalHeight = original.height.toDouble()
            val originalFormat = reader.formatName

            // リサイズ後のファイルを作成
            val resizedWidth  = width.toDouble()
            val resizedHeight = width * originalHeight / originalWidth
            val resized = BufferedImage(resizedWidth.roundToInt(), resizedHeight.roundToInt(), original.type)

            // 変換器
            val transformer = AffineTransformOp(
                    AffineTransform.getScaleInstance( resizedWidth / originalWidth, resizedHeight / originalHeight)
                    , AffineTransformOp.TYPE_BILINEAR
            )

            // 変換
            transformer.filter(original, resized)

            // 変換した結果を書き込む
            val result = ByteArrayOutputStream()
            ImageIO.write(resized, originalFormat, result)

            return result.toByteArray()

        } catch (e: Exception) {
            throw InvalidFileException("failed to resize", e)
        } finally {
            input.close()
            byteArrayStream.close()
        }

    }


    class InvalidFileException: Exception{
        constructor(message: String) :super(message)
        constructor(e: Exception) : super(e.cause)
        constructor(message: String, e: Exception) :super(message, e)
    }

}