package com.special.domain.datasources

import com.special.domain.entities.Coordinate
import com.special.domain.entities.Place
import com.special.domain.entities.PlaceCategory
import com.special.domain.entities.RequestPlace

interface PlaceRemoteDataSource {
    suspend fun allPlaces(): List<Place>

    suspend fun boundsPlaces(from: Coordinate, to: Coordinate): List<Place>

    suspend fun registerPlace(request: RequestPlace)

    suspend fun categories(): List<PlaceCategory>
}