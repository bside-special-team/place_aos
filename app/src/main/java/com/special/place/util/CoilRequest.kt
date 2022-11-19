package com.special.place.util

import android.content.Context
import coil.request.ImageRequest
import com.special.domain.datasources.TokenDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CoilRequest @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tokenData: TokenDataSource
) {

    fun myImageRequest(url: String): ImageRequest {
        return ImageRequest.Builder(context)
            .data(url)
            .addHeader("Authorization", "Bearer ${tokenData.accessToken()}")
            .build()
    }
}