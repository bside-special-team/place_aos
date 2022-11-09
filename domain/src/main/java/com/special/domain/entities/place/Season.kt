package com.special.domain.entities.place

import com.google.gson.annotations.SerializedName

enum class Season {
    @SerializedName("SPRING")
    Spring,
    @SerializedName("SUMMER")
    Summer,
    @SerializedName("AUTUMN")
    Autumn,
    @SerializedName("WINTER")
    Winter
}