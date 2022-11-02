package com.special.remote

import com.special.remote.impls.RemoteDataImpl


const val url = "http://115.85.181.70:8080/"

suspend fun main(args: Array<String>) {
    val api = ApiManager(baseUrl = url)
    val placeData = RemoteDataImpl(api)

    val result = placeData.allPlaces()

    result.forEach {
        println(it)
    }
}