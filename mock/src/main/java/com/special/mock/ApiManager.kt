package com.special.mock

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiManager constructor(
    private val baseUrl: String,
    private val interceptors: List<Interceptor> = listOf(),
    private val networkInterceptor: List<Interceptor> = listOf()
) {

    private val client: OkHttpClient by lazy {
        val httpClientBuilder = OkHttpClient.Builder()

        httpClientBuilder
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)

        interceptors.forEach {
            httpClientBuilder.addInterceptor(it)
        }

        networkInterceptor.forEach {
            httpClientBuilder.addNetworkInterceptor(it)
        }

        httpClientBuilder.build()
    }

    fun <T> create(service: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(service)
    }

}