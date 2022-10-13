package com.special.data.utils

import android.graphics.Bitmap
import java.io.FileOutputStream

object BitmapConverter {


    fun outStreamToWebpBitmap(width: Int, height: Int, out: FileOutputStream): Bitmap {
        return Bitmap.createBitmap(width, height, Bitmap.Config.RGBA_F16).apply {
            compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 100, out)
            out.close()
        }
    }

}