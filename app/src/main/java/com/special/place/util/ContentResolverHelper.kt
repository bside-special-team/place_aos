package com.special.place.util

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class ContentResolverHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun uriToFile(uri: Uri): File? {
        return getPath(uri)?.let { File(it) }
    }

    private fun getPath(uri: Uri): String? {
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId: String = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return "${Environment.getExternalStorageDirectory().absolutePath}/${split[1]}"
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                val id: String = DocumentsContract.getDocumentId(uri)
                val contentUri: Uri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), id.toLong()
                )
                return getDataColumn(contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId: String = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val contentUri: Uri = when (split[0]) { // TYPE
                    "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    else -> uri
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1]) // ID
                return getDataColumn(contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return getDataColumn(uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private fun getDataColumn(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor?.close()
        }
        return null
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }
}