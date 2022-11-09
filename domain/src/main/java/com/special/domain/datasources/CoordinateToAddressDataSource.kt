package com.special.domain.datasources

import com.special.domain.entities.place.Coordinate

interface CoordinateToAddressDataSource {
    suspend fun coordinateToAddress(coordinate: Coordinate): String
}