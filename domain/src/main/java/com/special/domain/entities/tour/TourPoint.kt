package com.special.domain.entities.tour

import com.special.domain.entities.place.Coordinate

data class TourPoint(
    val name: String?,
    val tags: List<String>,
    val coordinate: Coordinate,
    val imagePath: String
)
