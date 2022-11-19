package com.special.data.source

import com.special.data.utils.PrefsHelper
import com.special.domain.datasources.TokenDataSource
import com.special.domain.entities.user.LoginToken
import com.special.domain.entities.user.LoginType
import javax.inject.Inject

class TokenDataSourceImpl @Inject constructor(
    private val prefsHelper: PrefsHelper
) : TokenDataSource {
    override val isLogin: Boolean = prefsHelper.isLogin
    override val loginType: LoginType = prefsHelper.loginType

    override fun accessToken(): String {
        return prefsHelper.accessToken ?: ""
    }

    override fun refreshToken(): String {
        return prefsHelper.refreshToken ?: ""
    }

    override fun tokenRaw(): LoginToken {
        return LoginToken(
            accessToken = accessToken(),
            refreshToken = refreshToken()
        )
    }

    override fun updateToken(token: LoginToken) {
        prefsHelper.accessToken = token.accessToken
        prefsHelper.refreshToken = token.refreshToken
    }
}