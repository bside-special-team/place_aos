package com.special.domain.entities

import java.time.LocalTime

data class RequestPlace(
    val coordinate: Coordinate,
    val name: String,
    val description: String,
    val baseStartTime: LocalTime,
    val baseEndTime: LocalTime,
    val hashTags: List<String>,
    val season: Season
)
