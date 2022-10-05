package com.special.remote.apis

import com.special.domain.entities.PlaceCategory
import com.special.remote.models.PlaceResponseModel
import com.special.domain.entities.RequestPlace
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PlaceApi {

    @GET("/api/v1/places")
    suspend fun allPlaces(): PlaceResponseModel

    @POST("/api/v1/places")
    suspend fun registerPlaces(@Body request: RequestPlace)

    @GET("/api/v1/places/coordinate")
    suspend fun coordinatePlaces(
        @Query("fromLatitude") fromLat: String,
        @Query("fromLongitude") fromLng: String,
        @Query("toLatitude") toLat: String,
        @Query("toLongitude") toLng: String,
    ): PlaceResponseModel

    @GET("/api/v1/categories")
    suspend fun categories(): List<PlaceCategory>


}