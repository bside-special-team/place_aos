package com.special.domain.entities.user

data class User(
    val id: String,
    val loginType: LoginType,
    val nickName: String
)