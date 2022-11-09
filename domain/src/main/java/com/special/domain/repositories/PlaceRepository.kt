package com.special.domain.repositories

import com.special.domain.entities.Paging
import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.NearPlaces
import com.special.domain.entities.place.Place
import com.special.domain.entities.place.RequestRegisterPlace
import com.special.domain.entities.place.comment.Comment
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    fun updateCoordinate(coordinates: Pair<Coordinate, Coordinate>)

    val places: Flow<List<Place>>

    suspend fun registerPlace(request: RequestRegisterPlace)

    suspend fun visitPlace(targetId: String)

    suspend fun likePlace(targetId: String)

    suspend fun unLikePlace(targetId: String)

    suspend fun commentList(placeId: String, page: Int): Paging<Comment>

    suspend fun nearPlaceCount(coordinate: Coordinate): NearPlaces
}