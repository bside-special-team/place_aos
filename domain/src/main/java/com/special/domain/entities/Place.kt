package com.special.domain.entities

data class Place(
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val tags: List<String>
)
