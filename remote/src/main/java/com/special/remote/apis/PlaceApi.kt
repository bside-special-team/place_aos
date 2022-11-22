package com.special.remote.apis

import com.special.domain.entities.BaseResponse
import com.special.domain.entities.place.Place
import com.special.domain.entities.place.RequestRegisterPlace
import com.special.domain.entities.user.Comment
import com.special.domain.entities.user.CommentRequest
import com.special.domain.entities.user.CommentResponse
import com.special.domain.entities.user.User
import com.special.remote.models.PlaceResponseModel
import okhttp3.MultipartBody
import retrofit2.http.*

interface PlaceApi {

    @GET("/api/v1/places")
    suspend fun allPlaces(): PlaceResponseModel

    @POST("/api/v1/places")
    suspend fun registerPlaces(@Body request: RequestRegisterPlace)

    @PUT("/api/v1/places")
    suspend fun modifyPlaces(@Body request: RequestRegisterPlace)

    @DELETE("/api/v1/places")
    suspend fun removePlaces(@Field("placeId") placeId: String)

    @GET("/api/v1/places/coordinate")
    suspend fun coordinatePlaces(
        @Query("fromLatitude") fromLat: String,
        @Query("fromLongitude") fromLng: String,
        @Query("toLatitude") toLat: String,
        @Query("toLongitude") toLng: String,
    ): PlaceResponseModel

    @POST("/api/v1/places/check-in")
    suspend fun visitPlace(@Field("placeId") placeId: String): BaseResponse<Place>

    @POST("/api/v1/places/recommendation")
    suspend fun recommendPlace(@Field("placeId") placeId: String): BaseResponse<Place>

    @Multipart
    @POST("/api/v1/images")
    suspend fun uploadImage(@Part images: List<MultipartBody.Part>): List<String>

    @POST("/api/v1/comments")
    suspend fun registerComments(@Body comment: CommentRequest): BaseResponse<Comment>

    @GET("/api/v1/comments/places/{placeId}")
    suspend fun placeComments(@Path("placeId") placeId: String, lastTimeStamp: Long, limit: Int): CommentResponse

    @PUT("/api/v1/users/update")
    suspend fun updateNickname(@Field("nickName") nickName: String)

    @GET("/api/v1/users/one")
    suspend fun checkUser(): BaseResponse<User>
}