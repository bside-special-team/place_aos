package com.special.data.utils

import android.graphics.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.internal.closeQuietly
import java.io.ByteArrayOutputStream


object BitmapConverter {

    suspend fun convertWebpBitmap(bitmap: Bitmap): Bitmap {
        return withContext(Dispatchers.IO) {
            val out = ByteArrayOutputStream()

            bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 90, out)
            val bytes = out.toByteArray()

            out.closeQuietly()

            println("WEBP Size: ${bytes.size / 1024 / 1024.0}MB")

            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }
    }

    private const val RESIZED_IMAGE_SIZE = 72f

    private fun resizedBitmap(origin: Bitmap): Bitmap {
        val ratio = origin.width.toFloat() / origin.height

        val (resizeWidth: Float, resizeHeight: Float) = if (origin.width > origin.height)
            (RESIZED_IMAGE_SIZE * ratio) to RESIZED_IMAGE_SIZE
        else
            RESIZED_IMAGE_SIZE to (RESIZED_IMAGE_SIZE * ratio)

        return Bitmap.createScaledBitmap(origin, resizeWidth.toInt(), resizeHeight.toInt(), true)
    }

    private fun centerCropBitmap(origin: Bitmap): Bitmap {
        val (x: Float, y: Float) = if (origin.width > origin.height)
            (origin.width / 2 - RESIZED_IMAGE_SIZE / 2) to 0f
        else
            0f to (origin.height / 2 - RESIZED_IMAGE_SIZE / 2)

        return Bitmap.createBitmap(origin, x.toInt(), y.toInt(), RESIZED_IMAGE_SIZE.toInt(), RESIZED_IMAGE_SIZE.toInt())
    }

    private fun resizeCircleCropBitmap(origin: Bitmap): Bitmap {
        val copied = origin.copy(Bitmap.Config.ARGB_8888, true)
        val resizedBitmap = centerCropBitmap(resizedBitmap(copied))
        val result = Bitmap.createBitmap(resizedBitmap.width, resizedBitmap.height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(result)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = 0xff424242.toInt()

        val rect = Rect(0, 0, resizedBitmap.width, resizedBitmap.height)
        val rectF = RectF(rect)

        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawCircle(rectF.left + rectF.width() / 2, rectF.top + rectF.height() / 2, RESIZED_IMAGE_SIZE / 2, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(resizedBitmap, rect, rect, paint)

        return result
    }

    suspend fun mergedMarker(baseMarker: Bitmap, image: Bitmap): Bitmap {
        return withContext(Dispatchers.Default) {
            val resultMarker = Bitmap.createBitmap(baseMarker.width, baseMarker.height, Bitmap.Config.ARGB_8888)

            val resizeImage = resizeCircleCropBitmap(image)

            Canvas(resultMarker).apply {
                drawBitmap(baseMarker, 0f, 0f, null)
                drawBitmap(resizeImage, 19f, 19f, null)
            }

            resultMarker
        }
    }
}