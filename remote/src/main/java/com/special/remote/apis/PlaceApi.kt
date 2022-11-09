package com.special.remote.apis

import com.special.domain.entities.place.PlaceCategory
import com.special.domain.entities.place.RequestRegisterPlace
import com.special.domain.entities.place.RequestVisitPlace
import com.special.domain.entities.user.LoginToken
import com.special.remote.models.PlaceResponseModel
import okhttp3.MultipartBody
import retrofit2.http.*

interface PlaceApi {

    @GET("/api/v1/places")
    suspend fun allPlaces(): PlaceResponseModel

    @POST("/api/v1/places")
    suspend fun registerPlaces(@Body request: RequestRegisterPlace)

    @GET("/api/v1/places/coordinate")
    suspend fun coordinatePlaces(
        @Query("fromLatitude") fromLat: String,
        @Query("fromLongitude") fromLng: String,
        @Query("toLatitude") toLat: String,
        @Query("toLongitude") toLng: String,
    ): PlaceResponseModel

    @POST("/api/v1/places/check-in")
    suspend fun visitPlace(@Body request: RequestVisitPlace): String

    @GET("/api/v1/categories")
    suspend fun categories(): List<PlaceCategory>

    @Multipart
    @POST("/api/v1/images")
    suspend fun uploadImage(@Part file: MultipartBody.Part): String

    @GET("/login/oauth2/id-token")
    suspend fun socialLogin(@Query("idToken") idToken: String): LoginToken

}