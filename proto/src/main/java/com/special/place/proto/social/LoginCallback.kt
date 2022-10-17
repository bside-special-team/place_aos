package com.special.place.proto.social

interface LoginCallback {
    fun onResponse(loginResponse: LoginResponse)
    fun onFailed(error: Throwable)
}