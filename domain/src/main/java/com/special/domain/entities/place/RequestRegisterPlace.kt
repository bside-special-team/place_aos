package com.special.domain.entities.place

data class RequestRegisterPlace(
    val coordinate: Coordinate,
    val name: String? = null,
    val userId: String? = null,
    val imageUuids: List<String> = listOf(),
    val hashTags: List<String> = listOf(),
)