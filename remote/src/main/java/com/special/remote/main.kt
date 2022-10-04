package com.special.remote

import com.special.remote.impls.PlaceRemoteDataImpl


const val url = "http://115.85.181.70:8080/"

suspend fun main(args: Array<String>) {
    val api = ApiManager(baseUrl = url)
    val placeData = PlaceRemoteDataImpl(api)

    val result = placeData.allPlaces()

    if (result.isFailure) {
        println("is Api Failed!!")
    } else {
        println("api Success!!")
    }

    result.getOrNull()?.forEach {
        println(it)
    }
}