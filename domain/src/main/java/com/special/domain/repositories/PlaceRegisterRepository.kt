package com.special.domain.repositories

import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.PlaceCategory
import com.special.domain.entities.place.RequestRegisterPlace
import kotlinx.coroutines.flow.Flow

interface PlaceRegisterRepository {
    suspend fun registerPlace(request: RequestRegisterPlace)

    fun updateLocation(coordinate: Coordinate)

    val locationText: Flow<String>

    suspend fun categories(): List<PlaceCategory>

    suspend fun uploadImage(targetId: String, imagePath: String)


}