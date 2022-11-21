package com.special.domain.datasources

import com.special.domain.entities.user.LoginToken

interface LoginRemoteDataSource {
    suspend fun socialLogin(idToken: String): LoginToken
    suspend fun refreshToken(token: LoginToken): LoginToken
}