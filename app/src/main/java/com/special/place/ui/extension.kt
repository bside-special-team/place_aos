package com.special.place.ui

import com.special.domain.entities.place.PlaceType

fun PlaceType.text(): String = when (this) {
    PlaceType.Hidden -> "히든플레이스"
    PlaceType.LandMark -> "랜드마크"
}