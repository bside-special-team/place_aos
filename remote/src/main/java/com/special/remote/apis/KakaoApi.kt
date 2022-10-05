package com.special.remote.apis

import com.special.remote.models.CoordinateToAddressModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApi {
    @GET("v2/local/geo/coord2address.json")
    suspend fun coord2Address(
        @Query("x") longitude: String,
        @Query("y") latitude: String,
        @Query("input_coord") coordType: String = "WGS84"
    ): CoordinateToAddressModel
}