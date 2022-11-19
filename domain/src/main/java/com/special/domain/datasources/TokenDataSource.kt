package com.special.domain.datasources

import com.special.domain.entities.user.LoginToken
import com.special.domain.entities.user.LoginType

interface TokenDataSource {
    val loginType: LoginType
    val isLogin: Boolean
    fun accessToken(): String
    fun refreshToken(): String
    fun tokenRaw(): LoginToken

    fun updateToken(token: LoginToken)
}