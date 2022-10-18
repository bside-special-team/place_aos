package com.special.data.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.internal.closeQuietly
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

object BitmapConverter {

    suspend fun convertWebpBitmap(bitmap: Bitmap): Bitmap {
        return withContext(Dispatchers.IO) {
            val out = ByteArrayOutputStream()

            bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 90, out)
            val bytes = out.toByteArray()

            out.closeQuietly()

            println("WEBP Size: ${bytes.size/1024/1024.0}MB")

            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }
    }

}