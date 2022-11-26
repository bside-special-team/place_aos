package com.special.domain.entities.place

data class Coordinate(
    val latitude: String,
    val longitude: String
)

data class CoordinateBounds(
    val from: Coordinate,
    val to: Coordinate
)