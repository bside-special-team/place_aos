package com.special.domain.entities.user

import com.google.gson.annotations.SerializedName

data class User(
    val id: String,
    @SerializedName("authProvider")
    val loginType: LoginType?,
    val subject: String?,
    val email: String?,
    val nickName: String,
    val pushAlarm: Boolean = false,
    val marketingAlarm: Boolean = false,
    val point: Int,
    val label: String
)

data class NickNameUpdate(val nickName: String)