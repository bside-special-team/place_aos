package com.special.remote

import okhttp3.Interceptor
import okhttp3.Response

class KakaoApiKeyInterceptor constructor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "KakaoAK $apiKey")
            .build()

        return chain.proceed(newRequest)
    }
}