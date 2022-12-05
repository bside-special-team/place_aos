package com.special.domain.entities

import com.google.gson.annotations.SerializedName

enum class ReportType {
    @SerializedName("PLACE")
    Place,

    @SerializedName("COMMENT")
    Comment,

    @SerializedName("USER")
    User
}