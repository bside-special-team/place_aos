package com.special.domain.entities.tour

import com.special.domain.entities.place.Coordinate

data class Tour(
    val startTime: Long,
    val startCoordinate: Coordinate,
    val endTime: Long,
    val endCoordinate: Coordinate,
    val tourPoints: List<TourPoint>
)
