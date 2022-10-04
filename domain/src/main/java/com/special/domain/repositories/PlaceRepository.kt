package com.special.domain.repositories

import com.special.domain.entities.Coordinate
import com.special.domain.entities.Place
import com.special.domain.entities.RequestPlace
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    fun updateCoordinate(coordinates: Pair<Coordinate, Coordinate>)

    val places: Flow<List<Place>>

    suspend fun registerPlace(request: RequestPlace): Result<Unit>
}

abstract class BindModule {

}