package com.special.domain.entities.place

data class PlaceResponse(
    val hiddenPlaceList: List<Place>,
    val landMarkList: List<Place>,
    val hiddenPlaceCount: Int,
    val landMarkCount: Int
) {
    companion object {
        fun empty() = PlaceResponse(
            hiddenPlaceCount = 0,
            landMarkCount = 0,
            hiddenPlaceList = listOf(),
            landMarkList = listOf()
        )
    }
}
