package com.special.domain.entities.user

data class LoginToken(
    val accessToken: String,
    val refreshToken: String
)
