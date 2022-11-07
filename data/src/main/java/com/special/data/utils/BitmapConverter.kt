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

    private const val RESIZED_IMAGE_RATIO = 0.875
    private const val RESIZED_IMAGE_RADIUS_RATIO = 0.4

    private fun resizedBitmap(origin: Bitmap, resizedImageSize: Float): Bitmap {
        // 이미지의 가로 세로 비율
        val ratio = origin.width.toFloat() / origin.height

        // 가로, 세로 중 큰 쪽의 크기를 늘리기 위한 크기 비교
        val (resizeWidth: Float, resizeHeight: Float) = if (origin.width > origin.height)
            (resizedImageSize * ratio) to resizedImageSize
        else
            resizedImageSize to (resizedImageSize * ratio)

        return Bitmap.createScaledBitmap(origin, resizeWidth.toInt(), resizeHeight.toInt(), true)
    }

    private fun centerCropBitmap(origin: Bitmap, resizedImageSize: Float): Bitmap {
        // 이미지 가로, 세로 비교하여 큰 쪽을 잘라내어 정 사각 형으로 변경
        val (x: Float, y: Float) = if (origin.width > origin.height)
            (origin.width / 2 - resizedImageSize / 2) to 0f
        else
            0f to (origin.height / 2 - resizedImageSize / 2)

        return Bitmap.createBitmap(origin, x.toInt(), y.toInt(), resizedImageSize.toInt(), resizedImageSize.toInt())
    }

    private fun resizeCircleCropBitmap(origin: Bitmap, resizedImageSize: Float): Bitmap {
        val copied = origin.copy(Bitmap.Config.ARGB_8888, true)
        val resizedBitmap = centerCropBitmap(resizedBitmap(copied, resizedImageSize), resizedImageSize)
        val result = Bitmap.createBitmap(resizedBitmap.width, resizedBitmap.height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(result)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = 0xff424242.toInt()

        val rect = Rect(0, 0, resizedBitmap.width, resizedBitmap.height)
        val rectF = RectF(rect)

        // 이미지 크기로 radius 비율 계산
        val radius = (resizedImageSize * RESIZED_IMAGE_RADIUS_RATIO).toFloat()

        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawRoundRect(rectF, radius, radius, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(resizedBitmap, rect, rect, paint)

        return result
    }

    suspend fun mergedMarker(baseMarker: Bitmap, decoBadge: Bitmap, image: Bitmap): Bitmap {
        return withContext(Dispatchers.Default) {
            // 상단 장식 이미지 세로의 중간 값
            val decoOffsetHeight = decoBadge.height / 2f

            val resultMarker = Bitmap.createBitmap(baseMarker.width, (baseMarker.height + decoOffsetHeight).toInt(), Bitmap.Config.ARGB_8888)
            val resizeImage = resizeCircleCropBitmap(image, (baseMarker.width * RESIZED_IMAGE_RATIO).toFloat())

            // 배경 마커에 리사이즈 이미지를 중앙 정렬 하기 위한 계산
            val offset = (baseMarker.width - resizeImage.width) / 2f

            // 배경 마커에 상단 장식 이미지를 가로 중앙 배치 하기위한 계산
            val decoOffset = (baseMarker.width - decoBadge.width) / 2f

            Canvas(resultMarker).apply {
                //
                drawBitmap(baseMarker, 0f, decoOffsetHeight, null)
                drawBitmap(resizeImage, offset, offset + decoOffsetHeight, null)
                drawBitmap(decoBadge, decoOffset, 0f, null)
            }

            resultMarker
        }
    }
}