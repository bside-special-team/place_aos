package com.special.remote.impls

import com.special.domain.datasources.PlaceRemoteDataSource
import com.special.domain.entities.Coordinate
import com.special.domain.entities.Place
import com.special.domain.entities.RequestPlace
import com.special.remote.ApiManager
import com.special.remote.apis.PlaceApi
import javax.inject.Inject

class PlaceRemoteDataImpl @Inject constructor(apiManager: ApiManager) : PlaceRemoteDataSource {
    private val client = apiManager.create(PlaceApi::class.java)

    override suspend fun allPlaces(): Result<List<Place>> {
        return runCatching {
            client.allPlaces()
        }.map { it.landMarkPlaces + it.hiddenPlaces }
    }

    override suspend fun boundsPlaces(from: Coordinate, to: Coordinate): Result<List<Place>> {
        return runCatching {
            client.coordinatePlaces(fromLat = from.latitude, fromLng = from.longitude, toLat = to.latitude, toLng = to.longitude)
        }.map { it.landMarkPlaces + it.hiddenPlaces }
    }

    override suspend fun registerPlace(request: RequestPlace): Result<Unit> {
        return runCatching {
            client.registerPlaces(request)
        }
    }
}