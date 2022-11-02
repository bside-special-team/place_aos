package com.special.domain.entities.user

sealed class LoginStatus private constructor(
    val isLogin: Boolean,
    val type: LoginType
) {

    class LoggedIn(
        type: LoginType,
        val token: LoginToken
    ) : LoginStatus(isLogin = true, type = type)

    object LoggedOut : LoginStatus(isLogin = false, type = LoginType.None)

    companion object {
        fun success(type: LoginType, token: LoginToken): LoginStatus = LoggedIn(type, token)
        fun empty(): LoginStatus = LoggedOut
    }
}