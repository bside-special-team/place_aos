package com.special.data.repoimpl

import com.special.domain.datasources.RemoteDataSource
import com.special.domain.entities.Paging
import com.special.domain.entities.place.*
import com.special.domain.entities.place.comment.Comment
import com.special.domain.entities.place.comment.CommentRequest
import com.special.domain.entities.user.PointResult
import com.special.domain.repositories.PlaceRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepoImpl @Inject constructor(private val placeRemote: RemoteDataSource) :
    PlaceRepository {

    private val _currentPlace: MutableSharedFlow<Place> = MutableSharedFlow(replay = 1)
    override val currentPlace: Flow<Place>
        get() = _currentPlace

    override fun selectPlace(place: Place) {
        CoroutineScope(Dispatchers.Default).launch {
            _currentPlace.emit(place)
        }
    }

    private val coordinatesFlow: MutableSharedFlow<CoordinateBounds> = MutableSharedFlow(replay = 1)
    private val _pointResult: MutableStateFlow<PointResult> = MutableStateFlow(PointResult.empty())

    override val pointResult: Flow<PointResult> = _pointResult

    override fun updateCoordinate(coordinates: CoordinateBounds) {
        runBlocking { coordinatesFlow.emit(coordinates) }
    }

    @OptIn(FlowPreview::class)
    override val places: Flow<List<Place>>
        get() = coordinatesFlow.distinctUntilChanged().debounce(200).map {
            placeRemote.boundsPlaces(from = it.from, to = it.to).getOrDefault(PlaceResponse.empty())
        }.map {
            _placeCount.emit(NearPlaces(hiddenPlaceCount = it.hiddenPlaceCount, landmarkCount = it.landMarkCount))
            it.hiddenPlaceList + it.landMarkList
        }

    private val _placeCount: MutableSharedFlow<NearPlaces> = MutableSharedFlow(replay = 1)
    override val placeCount: Flow<NearPlaces>
        get() = _placeCount

    override suspend fun registerPlace(request: RequestRegisterPlace) {
        withContext(Dispatchers.IO) {
            placeRemote.registerPlace(request)
        }
    }

    override suspend fun visitPlace(targetId: String) {
        withContext(Dispatchers.IO) {
            _pointResult.emit(placeRemote.visitPlace(targetId))
        }
    }

    override suspend fun likePlace(targetId: String) {
        withContext(Dispatchers.IO) {
            _pointResult.emit(placeRemote.recommendPlace(targetId))
        }
    }

    override suspend fun unLikePlace(targetId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun nearPlaceCount(coordinate: Coordinate): NearPlaces {
        TODO("Not yet implemented")
    }

    override suspend fun modifyPlace(request: RequestRegisterPlace) {
        TODO("Not yet implemented")
    }

    override suspend fun removePlace(targetId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun reportPlace(placeId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun myPlaces(page: Int): Paging<Place> {
        TODO("Not yet implemented")
    }

    override suspend fun visitedPlaces(page: Int): Paging<Place> {
        TODO("Not yet implemented")
    }

    override suspend fun myLikePlace(page: Int): Paging<Place> {
        TODO("Not yet implemented")
    }

    override suspend fun commentList(targetId: String, lastTimestamp: Long): Paging<Comment> {
        return withContext(Dispatchers.IO) {
            placeRemote.commentList(targetId, lastTimestamp)
        }
    }

    override suspend fun registerComment(targetId: String, comment: String) {
        withContext(Dispatchers.IO) {
            _pointResult.emit(placeRemote.registerComment(CommentRequest(placeId = targetId, comment = comment)))
        }
    }
}