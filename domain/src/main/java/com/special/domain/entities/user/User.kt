package com.special.domain.entities.user

data class User(
    val id: String,
    val loginType: LoginType,
    val nickName: String,
    val pushAlarm: Boolean = false,
    val marketingAlarm: Boolean = false,
    val myPoint: Int,
    val myBadge: String?,
    val level: Int?,
    val progress: Float?
)