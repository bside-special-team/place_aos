package com.special.remote.apis

import com.special.domain.entities.BaseResponse
import com.special.domain.entities.place.Place
import com.special.domain.entities.place.PlaceResponse
import com.special.domain.entities.place.RequestRegisterPlace
import com.special.domain.entities.place.RequestReport
import com.special.domain.entities.place.comment.Comment
import com.special.domain.entities.place.comment.CommentModifyRequest
import com.special.domain.entities.place.comment.CommentRequest
import com.special.domain.entities.place.comment.CommentResponse
import com.special.domain.entities.user.LevelInfo
import com.special.domain.entities.user.NickNameUpdate
import com.special.domain.entities.user.User
import okhttp3.MultipartBody
import retrofit2.http.*

interface PlaceApi {

    @GET("/api/v1/places")
    suspend fun allPlaces(): PlaceResponse

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
    ): PlaceResponse

    @POST("/api/v1/places/check-in")
    suspend fun visitPlace(@Query("placeId") placeId: String): BaseResponse<Place>

    @POST("/api/v1/places/recommendation")
    suspend fun recommendPlace(@Query("placeId") placeId: String): BaseResponse<Place>

    @GET("/api/v1/places/recommendation")
    suspend fun myRecommendPlaces(): List<Place>

    @Multipart
    @POST("/api/v1/images")
    suspend fun uploadImage(@Part images: List<MultipartBody.Part>): List<String>

    @POST("/api/v1/comments")
    suspend fun registerComments(@Body comment: CommentRequest): BaseResponse<Comment>

    @DELETE("/api/v1/comments")
    suspend fun deleteComment(@Body request: CommentModifyRequest): BaseResponse<Comment>

    @PATCH("/api/v1/comments")
    suspend fun modifyComment(@Body request: CommentModifyRequest): BaseResponse<Comment>

    @GET
    suspend fun myComments(@Query("lastTimestamp") lastTimeStamp: Long, @Query("limit") limit: Int): CommentResponse

    @GET("/api/v1/comments/places/{placeId}")
    suspend fun placeComments(@Path("placeId") placeId: String, @Query("lastTimestamp") lastTimeStamp: Long, @Query("limit") limit: Int): CommentResponse

    @PUT("/api/v1/users/update")
    suspend fun updateNickname(@Body nickName: NickNameUpdate)

    @GET("/api/v1/users/one")
    suspend fun checkUser(): BaseResponse<User>

    @GET("/api/v1/users/levels")
    suspend fun levelInfo(): List<LevelInfo>

    @GET("/api/v1/places/recent-visited")
    suspend fun recentVisitPlaces(): List<Place>

    @GET("/api/v1/places/myPlace")
    suspend fun myPlaces(): List<Place>

    @POST("/api/v1/block")
    suspend fun block(@Body request: RequestReport)
}