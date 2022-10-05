package com.special.domain.datasources

import com.special.domain.entities.Coordinate

interface CoordinateToAddressDataSource {
    suspend fun coordinateToAddress(coordinate: Coordinate): String
}