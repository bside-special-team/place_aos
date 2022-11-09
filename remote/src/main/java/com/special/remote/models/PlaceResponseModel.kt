package com.special.remote.models

import com.special.domain.entities.place.Place

data class PlaceResponseModel(
    val hiddenPlaces: List<Place>,
    val landMarkPlaces: List<Place>
)
