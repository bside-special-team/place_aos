package com.special.domain.entities

data class User(
    val id: String,
    val loginType: LoginType,
    val nickName: String
)

sealed class LoginType {
    object Kakao : LoginType()
    object Google : LoginType()
    object Facebook : LoginType()
}