package com.special.data.repoimpl

import com.special.domain.datasources.RemoteDataSource
import com.special.domain.entities.Paging
import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.NearPlaces
import com.special.domain.entities.place.Place
import com.special.domain.entities.place.RequestPlace
import com.special.domain.entities.place.comment.Comment
import com.special.domain.repositories.PlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaceRepoImpl @Inject constructor(private val placeRemote: RemoteDataSource) :
    PlaceRepository {

    private val coordinatesFlow: MutableStateFlow<Pair<Coordinate, Coordinate>> = MutableStateFlow(Pair(Coordinate("0", "0"), Coordinate("0", "0")))

    override fun updateCoordinate(coordinates: Pair<Coordinate, Coordinate>) {
        runBlocking { coordinatesFlow.emit(coordinates) }
    }

    @OptIn(FlowPreview::class)
    override val places: Flow<List<Place>>
        get() = coordinatesFlow.debounce(200).map {
            runCatching { placeRemote.allPlaces() }.getOrNull() ?: listOf()
        }

    override suspend fun registerPlace(request: RequestPlace) {
        withContext(Dispatchers.IO) {
            placeRemote.registerPlace(request)
        }
    }

    override suspend fun visitPlace(targetId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun likePlace(targetId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun unLikePlace(targetId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun commentList(placeId: String, page: Int): Paging<Comment> {
        TODO("Not yet implemented")
    }

    override suspend fun nearPlaceCount(coordinate: Coordinate): NearPlaces {
        TODO("Not yet implemented")
    }
}