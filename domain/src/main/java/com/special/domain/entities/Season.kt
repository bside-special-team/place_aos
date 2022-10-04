package com.special.domain.entities

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