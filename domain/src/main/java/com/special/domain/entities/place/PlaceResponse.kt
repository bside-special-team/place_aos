package com.special.domain.entities.place

data class PlaceResponse(
    val hiddenPlaces: List<Place>,
    val landMarkPlaces: List<Place>,
    val hiddenPlaceCount: Int,
    val landMarkCount: Int
) {
    companion object {
        fun empty() = PlaceResponse(
            hiddenPlaceCount = 0,
            landMarkCount = 0,
            hiddenPlaces = listOf(),
            landMarkPlaces = listOf()
        )
    }
}
