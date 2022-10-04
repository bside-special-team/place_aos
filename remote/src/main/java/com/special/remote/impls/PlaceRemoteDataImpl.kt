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

    override suspend fun allPlaces(): List<Place> {
        return client.allPlaces().let { it.landMarkPlaces + it.hiddenPlaces }
    }

    override suspend fun boundsPlaces(from: Coordinate, to: Coordinate): List<Place> {
        return client.coordinatePlaces(fromLat = from.latitude, fromLng = from.longitude, toLat = to.latitude, toLng = to.longitude)
            .let { it.landMarkPlaces + it.hiddenPlaces }
    }

    override suspend fun registerPlace(request: RequestPlace) {
        client.registerPlaces(request)
    }
}