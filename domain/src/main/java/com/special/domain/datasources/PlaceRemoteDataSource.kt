package com.special.domain.datasources

import com.special.domain.entities.Coordinate
import com.special.domain.entities.Place
import com.special.domain.entities.RequestPlace

interface PlaceRemoteDataSource {
    suspend fun allPlaces(): Result<List<Place>>

    suspend fun boundsPlaces(from: Coordinate, to: Coordinate): Result<List<Place>>

    suspend fun registerPlace(request: RequestPlace): Result<Unit>
}