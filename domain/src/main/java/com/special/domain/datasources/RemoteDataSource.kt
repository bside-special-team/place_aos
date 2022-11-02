package com.special.domain.datasources

import com.special.domain.entities.*
import com.special.domain.entities.user.LoginToken

interface RemoteDataSource {
    suspend fun allPlaces(): List<Place>

    suspend fun boundsPlaces(from: Coordinate, to: Coordinate): List<Place>

    suspend fun registerPlace(request: RequestPlace)

    suspend fun categories(): List<PlaceCategory>

    suspend fun socialLogin(idToken: String): LoginToken
}