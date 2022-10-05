package com.special.data.repoimpl

import com.special.domain.datasources.CoordinateToAddressDataSource
import com.special.domain.datasources.PlaceRemoteDataSource
import com.special.domain.entities.Coordinate
import com.special.domain.entities.PlaceCategory
import com.special.domain.entities.RequestPlace
import com.special.domain.repositories.PlaceRegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaceRegisterRepoImpl @Inject constructor(
    private val placeRemote: PlaceRemoteDataSource,
    private val coord2AddrRemote: CoordinateToAddressDataSource
): PlaceRegisterRepository {
    private val coordinateFlow: MutableSharedFlow<Coordinate> = MutableSharedFlow()

    override suspend fun registerPlace(request: RequestPlace) {
        withContext(Dispatchers.IO) {
            placeRemote.registerPlace(request)
        }
    }

    override fun updateLocation(coordinate: Coordinate) {
        runBlocking { coordinateFlow.emit(coordinate) }
    }

    @OptIn(FlowPreview::class)
    override val locationText: Flow<String>
        get() = coordinateFlow.debounce(100).map {
            coord2AddrRemote.coordinateToAddress(it)
        }

    override suspend fun categories(): List<PlaceCategory> {
        return withContext(Dispatchers.IO) {
            placeRemote.categories()
        }
    }
}