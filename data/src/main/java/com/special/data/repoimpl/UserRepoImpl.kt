package com.special.data.repoimpl

import android.util.Log
import com.special.domain.datasources.LoginRemoteDataSource
import com.special.domain.datasources.RemoteDataSource
import com.special.domain.datasources.TokenDataSource
import com.special.domain.entities.user.*
import com.special.domain.entities.user.badge.Badge
import com.special.domain.exception.RetrySocialLogin
import com.special.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepoImpl @Inject constructor(
    private val loginRemote: LoginRemoteDataSource,
    private val remote: RemoteDataSource,
    private val tokenData: TokenDataSource
) : UserRepository {

    private val _loginStatus: MutableSharedFlow<LoginStatus> = MutableSharedFlow(replay = 1)
    override val loginStatus: Flow<LoginStatus> = _loginStatus
    private var _user: User? = null

    override fun currentUser(): User? {
        return _user
    }

    init {
        CoroutineScope(Dispatchers.Default).launch {
            runCatching {
                if (tokenData.isLogin) {
                    _loginStatus.emit(LoginStatus.success(tokenData.loginType, loadToken()))
                    withContext(Dispatchers.IO) {
                        _user = remote.checkUser()
                    }
                } else {
                    _loginStatus.emit(LoginStatus.empty())
                }
            }.onFailure {
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

                _user = remote.checkUser()

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
        withContext(Dispatchers.IO) {
            remote.updateNickName(nickName)
        }
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

    override suspend fun nextLevel(): LevelInfo {
        return withContext(Dispatchers.IO) {
            val userData = _user
            if (userData != null) {
                val allLevelInfo = remote.levelInfo()
                allLevelInfo.find { it.minPoint > userData.point } ?: allLevelInfo.last()
            } else {
                LevelInfo.none()
            }
        }
    }

    override suspend fun deleteComment(commentId: String) {
        withContext(Dispatchers.IO) {
            remote.deleteComment(commentId)
        }
    }
}