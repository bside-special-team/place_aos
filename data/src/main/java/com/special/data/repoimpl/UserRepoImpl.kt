package com.special.data.repoimpl

import android.util.Log
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepoImpl @Inject constructor(
    private val loginRemote: LoginRemoteDataSource,
    private val tokenData: TokenDataSource
) : UserRepository {


    private val _loginStatus: MutableStateFlow<LoginStatus> = MutableStateFlow(LoginStatus.empty())
    override val loginStatus: Flow<LoginStatus> = _loginStatus

    init {
        CoroutineScope(Dispatchers.Default).launch {
            runCatching {
                if (tokenData.isLogin) {
                    _loginStatus.emit(LoginStatus.success(tokenData.loginType, loadToken()))
                } else {
                    Log.d("loginUserRepo", "isLogin == false")
                    _loginStatus.emit(LoginStatus.empty())
                }
            }.onFailure {
                Log.d("loginUserRepo", "loadToken failed")
                _loginStatus.emit(LoginStatus.empty())
            }
        }
    }

    override suspend fun socialLogin(response: SocialLoginResponse) {
        val statue = withContext(Dispatchers.IO) {
            if (response.isLogin && response.idToken != null) {
                val token = loginRemote.socialLogin(response.idToken!!)
                tokenData.updateLoginType(response.type)
                tokenData.updateToken(token)

                LoginStatus.success(response.type, token)
            } else {
                LoginStatus.empty()
            }
        }

        withContext(Dispatchers.Default) {
            Log.d("loginUserRepo", "isLogin == ${statue.isLogin}")
            val result = _loginStatus.tryEmit(statue)

            Log.d("loginUserRepo", "emit!, $result")
        }
    }

    override suspend fun unregister() {
        TODO("Not yet implemented")
    }

    override suspend fun myBadges(): List<Badge> {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        withContext(Dispatchers.Default) {
            tokenData.clearAll()
            _loginStatus.emit(LoginStatus.empty())
        }
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