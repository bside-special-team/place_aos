package com.special.remote


const val url = "http:/special-dev.xyz/"

suspend fun main(args: Array<String>) {
    val api = ApiManager(baseUrl = url)
//    val placeData = RemoteDataImpl(api)
//
//    val result = placeData.allPlaces()
//
//    result.getOrNull()?.forEach {
//        println(it)
//    }
}