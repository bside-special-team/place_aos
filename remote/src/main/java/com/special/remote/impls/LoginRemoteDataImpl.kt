package com.special.remote.impls

import com.special.domain.datasources.LoginRemoteDataSource
import com.special.domain.entities.user.LoginToken
import com.special.remote.ApiManager
import com.special.remote.PlaceLoginApiManager
import com.special.remote.apis.LoginApi
import javax.inject.Inject

class LoginRemoteDataImpl @Inject constructor(
    @PlaceLoginApiManager apiManager: ApiManager
) : LoginRemoteDataSource {
    private val client = apiManager.create(LoginApi::class.java)

    override suspend fun socialLogin(idToken: String): LoginToken {
        return client.socialLogin(idToken)
    }

    override suspend fun refreshToken(token: LoginToken): LoginToken {
        return client.tokenRefresh(token)
    }


}