package com.special.domain.entities

data class RequestPlace(
    val coordinate: Coordinate,
    val name: String? = null,
    val description: String? = null,
    val bestStartTime: String? = null,
    val bestEndTime: String? = null,
    val categoryCode: String? = null,
    val hashTags: List<String> = listOf(),
    val season: Season? = null
)
