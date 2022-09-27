package com.special.mock


const val url = "https://api.odcloud.kr/api/"

suspend fun main(args: Array<String>) {
    val api = ApiManager(baseUrl = url)
    val chargerData = ChargerMockDataImpl(api)

    chargerData.getChargers(1, 1000).getOrNull()?.forEach {
        println(it)
    }
}