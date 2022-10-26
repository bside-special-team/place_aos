package com.special.place.proto.social

data class LoginResponse(
    val uuid: String,
    val email: String?,
    val profileImage: String?,
    val displayName: String?
)
