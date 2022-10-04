package com.special.domain.entities

import com.google.gson.annotations.SerializedName

enum class PlaceType {
    @SerializedName("HIDDEN")
    Hidden,
    @SerializedName("LAND_MARK")
    LandMark
}