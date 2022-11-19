package com.special.data.repoimpl

import com.special.domain.datasources.CoordinateToAddressDataSource
import com.special.domain.datasources.RemoteDataSource
import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.RequestRegisterPlace
import com.special.domain.repositories.PlaceRegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class PlaceRegisterRepoImpl @Inject constructor(
    private val placeRemote: RemoteDataSource,
    private val coord2AddrRemote: CoordinateToAddressDataSource
): PlaceRegisterRepository {
    private val coordinateFlow: MutableSharedFlow<Coordinate> = MutableSharedFlow()

    override suspend fun registerPlace(request: RequestRegisterPlace) {
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

    override suspend fun uploadImage(imageFiles: List<File>): List<String> {
        return withContext(Dispatchers.IO) {
            placeRemote.uploadImage(imageFiles).getOrNull() ?: listOf()
        }
    }
}