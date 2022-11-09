package com.special.domain.datasources

import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.Place
import com.special.domain.entities.place.PlaceCategory
import com.special.domain.entities.place.RequestRegisterPlace
import com.special.domain.entities.user.LoginToken

interface RemoteDataSource {
    suspend fun allPlaces(): List<Place>

    suspend fun boundsPlaces(from: Coordinate, to: Coordinate): List<Place>

    suspend fun registerPlace(request: RequestRegisterPlace)

    suspend fun categories(): List<PlaceCategory>

    suspend fun socialLogin(idToken: String): LoginToken
}