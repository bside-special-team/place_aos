package com.special.domain.datasources

import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.Place
import com.special.domain.entities.place.RequestRegisterPlace
import java.io.File

interface RemoteDataSource {
    suspend fun allPlaces(): Result<List<Place>>

    suspend fun boundsPlaces(from: Coordinate, to: Coordinate): Result<List<Place>>

    suspend fun registerPlace(request: RequestRegisterPlace)

    suspend fun uploadImage(files: List<File>): Result<List<String>>
}