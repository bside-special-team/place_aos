package com.special.domain.entities.place

import com.google.gson.annotations.SerializedName

enum class PlaceType {
    @SerializedName("HIDDEN")
    Hidden,
    @SerializedName("LAND_MARK")
    LandMark
}