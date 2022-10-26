package com.special.place.proto

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import java.io.FileOutputStream

fun Context.uriToBitmap(uri: Uri): Bitmap =
    ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri))

fun Bitmap.compressWebp(out: FileOutputStream): Bitmap =
    apply { compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 100, out) }