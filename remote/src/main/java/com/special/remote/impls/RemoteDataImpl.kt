package com.special.remote.impls

import com.special.domain.datasources.RemoteDataSource
import com.special.domain.entities.*
import com.special.domain.entities.user.LoginToken
import com.special.remote.ApiManager
import com.special.remote.PlaceAppApiManager
import com.special.remote.apis.PlaceApi
import javax.inject.Inject

class RemoteDataImpl @Inject constructor(
    @PlaceAppApiManager apiManager: ApiManager
) : RemoteDataSource {
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

    override suspend fun categories(): List<PlaceCategory> {
        return client.categories()
    }

    override suspend fun socialLogin(idToken: String): LoginToken {
        return client.socialLogin(idToken)
    }
}