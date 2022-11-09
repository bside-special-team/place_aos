package com.special.domain.repositories

import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.Place
import com.special.domain.entities.place.RequestPlace
import com.special.domain.entities.place.comment.Comment
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    fun updateCoordinate(coordinates: Pair<Coordinate, Coordinate>)

    val places: Flow<List<Place>>

    suspend fun registerPlace(request: RequestPlace)

    suspend fun visitPlace(targetId: String)

    suspend fun likePlace(targetId: String)

    suspend fun commentList(placeId: String): List<Comment>

    suspend fun reportComment(commentId: String)

    suspend fun reportPlace(placeId: String)


}