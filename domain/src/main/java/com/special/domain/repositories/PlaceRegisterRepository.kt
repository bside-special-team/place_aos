package com.special.domain.repositories

import com.special.domain.entities.Coordinate
import com.special.domain.entities.PlaceCategory
import com.special.domain.entities.RequestPlace
import kotlinx.coroutines.flow.Flow

interface PlaceRegisterRepository {
    suspend fun registerPlace(request: RequestPlace)

    fun updateLocation(coordinate: Coordinate)

    val locationText: Flow<String>

    suspend fun categories(): List<PlaceCategory>
}