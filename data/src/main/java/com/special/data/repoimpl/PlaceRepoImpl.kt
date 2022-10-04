package com.special.data.repoimpl

import com.special.domain.datasources.PlaceRemoteDataSource
import com.special.domain.entities.Coordinate
import com.special.domain.entities.Place
import com.special.domain.entities.RequestPlace
import com.special.domain.repositories.PlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaceRepoImpl @Inject constructor(private val placeRemote: PlaceRemoteDataSource) :
    PlaceRepository {

    private val coordinatesFlow: MutableStateFlow<Pair<Coordinate, Coordinate>> = MutableStateFlow(Pair(Coordinate("0", "0"), Coordinate("0", "0")))

    override fun updateCoordinate(coordinates: Pair<Coordinate, Coordinate>) {
        runBlocking { coordinatesFlow.emit(coordinates) }
    }

    @OptIn(FlowPreview::class)
    override val places: Flow<List<Place>>
        get() = coordinatesFlow.debounce(200).map {
            placeRemote.allPlaces()
        }

    override suspend fun registerPlace(request: RequestPlace) {
        withContext(Dispatchers.IO) {
            placeRemote.registerPlace(request)
        }
    }
}