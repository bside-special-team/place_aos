package com.special.domain.entities.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userId")
    val id: String,
    @SerializedName("authProvider")
    val loginType: LoginType?,
    val subject: String?,
    val email: String?,
    val nickName: String?,
    val pushAlarm: Boolean = false,
    val marketingAlarm: Boolean = false,
    val myPoint: Int,
    val myBadge: String?,
    val level: Int?,
    val progress: Float?,
    val point: Int,
    val label: String
)

data class NickNameUpdate(val nickName: String)