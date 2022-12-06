package com.special.domain.repositories

import com.special.domain.entities.Paging
import com.special.domain.entities.place.*
import com.special.domain.entities.place.comment.Comment
import com.special.domain.entities.user.PointResult
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    fun updateCoordinate(coordinates: CoordinateBounds)

    val places: Flow<List<Place>>
    val placeCount: Flow<NearPlaces>
    val pointResult: Flow<PointResult>
    val currentPlace: Flow<Place>

    fun selectPlace(place: Place)

    suspend fun registerPlace(request: RequestRegisterPlace)

    suspend fun visitPlace(targetId: String)

    suspend fun likePlace(targetId: String)

    suspend fun unLikePlace(targetId: String)

    suspend fun nearPlaceCount(coordinate: Coordinate): NearPlaces

    suspend fun modifyPlace(request: RequestRegisterPlace)

    suspend fun removePlace(targetId: String)

    suspend fun reportPlace(placeId: String)

    suspend fun myPlaces(page: Int): Paging<Place>

    suspend fun visitedPlaces(page: Int): Paging<Place>

    suspend fun myLikePlace(page: Int): Paging<Place>

    suspend fun myComments(page: Int): Paging<CommentPlace>

    suspend fun commentList(targetId: String, lastTimestamp: Long): Paging<Comment>

    suspend fun registerComment(targetId: String, comment: String)
}