package com.special.data.repoimpl

import com.special.domain.datasources.LoginRemoteDataSource
import com.special.domain.datasources.TokenDataSource
import com.special.domain.entities.user.LoginStatus
import com.special.domain.entities.user.LoginToken
import com.special.domain.entities.user.SocialLoginResponse
import com.special.domain.entities.user.badge.Badge
import com.special.domain.exception.RetrySocialLogin
import com.special.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val loginRemote: LoginRemoteDataSource,
    private val tokenData: TokenDataSource
) : UserRepository {

    override val loginStatus: MutableSharedFlow<LoginStatus> = MutableSharedFlow()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            runCatching {
                if (tokenData.isLogin) {
                    loginStatus.emit(LoginStatus.success(tokenData.loginType, loadToken()))
                } else {
                    loginStatus.emit(LoginStatus.empty())
                }
            }.onFailure {
                loginStatus.emit(LoginStatus.empty())
            }
        }
    }

    override suspend fun socialLogin(response: SocialLoginResponse) {
        CoroutineScope(Dispatchers.IO).launch {
            if (response.isLogin && response.idToken != null) {
                val token = loginRemote.socialLogin(response.idToken!!)
                tokenData.updateToken(token)

                loginStatus.emit(LoginStatus.success(response.type, token))
            } else {
                loginStatus.emit(LoginStatus.empty())
            }
        }
    }

    override suspend fun unregister() {
        TODO("Not yet implemented")
    }

    override suspend fun myBadges(): List<Badge> {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override suspend fun updatePushAlarm(enable: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun updateMarketingAlarm(enable: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun modifyNickName(nickName: String) {
        TODO("Not yet implemented")
    }

    private fun loadToken(): LoginToken {
        val accessToken = tokenData.accessToken()
        val refreshToken = tokenData.refreshToken()

        return if (accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
            LoginToken(accessToken, refreshToken)
        } else {
            throw RetrySocialLogin()
        }
    }


}