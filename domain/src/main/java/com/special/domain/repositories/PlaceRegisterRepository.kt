package com.special.domain.repositories

import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.RequestRegisterPlace
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PlaceRegisterRepository {
    suspend fun registerPlace(request: RequestRegisterPlace)

    fun updateLocation(coordinate: Coordinate)

    val locationText: Flow<String>

    suspend fun uploadImage(imageFiles: List<File>): List<String>
}