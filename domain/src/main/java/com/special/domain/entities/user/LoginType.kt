package com.special.domain.entities.user

import com.google.gson.annotations.SerializedName

enum class LoginType {
    @SerializedName("KAKAO")
    Kakao,

    @SerializedName("GOOGLE")
    Google,
    None
}
