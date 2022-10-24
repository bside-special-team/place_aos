package com.special.place.proto.social

import kotlinx.coroutines.flow.Flow

interface LoginFactory {
    fun doLogin(type: LoginType)
    fun logout(type: LoginType)
    val result: Flow<Result<LoginResponse>>
}