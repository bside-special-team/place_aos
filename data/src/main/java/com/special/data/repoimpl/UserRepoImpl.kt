package com.special.data.repoimpl

import android.util.Log
import com.special.domain.datasources.LoginRemoteDataSource
import com.special.domain.datasources.RemoteDataSource
import com.special.domain.datasources.TokenDataSource
import com.special.domain.entities.place.Place
import com.special.domain.entities.user.*
import com.special.domain.entities.user.badge.Badge
import com.special.domain.exception.ExceptionListener
import com.special.domain.exception.RetrySocialLogin
import com.special.domain.repositories.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepoImpl @Inject constructor(
    private val loginRemote: LoginRemoteDataSource,
    private val remote: RemoteDataSource,
    private val tokenData: TokenDataSource,
    private val exceptionListener: ExceptionListener
) : UserRepository {

    private val _loginStatus: MutableSharedFlow<LoginStatus> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val loginStatus: Flow<LoginStatus> = _loginStatus
    private var _user: MutableStateFlow<User> = MutableStateFlow(User.mock())

    override val currentUser: Flow<User> = _user

    init {
        CoroutineScope(Dispatchers.Default).launch {
            runCatching {
                if (tokenData.isLogin) {
                    _loginStatus.emit(LoginStatus.success(tokenData.loginType, loadToken()))
                    withContext(Dispatchers.IO) {
                        val user = remote.checkUser()
                        _user.emit(user)
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
        withContext(Dispatchers.IO) {
            runCatching {
                val status = if (response.isLogin && response.idToken != null) {
                    val token = loginRemote.socialLogin(response.idToken!!)
                    tokenData.updateLoginType(response.type)
                    tokenData.updateToken(token)

                    val user = remote.checkUser()
                    _user.emit(user)

                    LoginStatus.success(response.type, token)
                } else {
                    exceptionListener.updateException(IllegalAccessException("소셜로그인에 실패 하였습니다."))

                    LoginStatus.empty()
                }

                val result = _loginStatus.tryEmit(status)

                Log.d("loginUserRepo", "emit!, $result")
            }.onFailure {
                it.printStackTrace()
                exceptionListener.updateException(it)
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
            val user = remote.checkUser()
            _user.emit(user)
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
            val userData = _user.value
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

    override suspend fun modifyComment(commentId: String, comment: String) {
        withContext(Dispatchers.IO) {
            remote.modifyComment(commentId = commentId, comment = comment)
        }
    }

    override suspend fun recentPlaces(): List<Place> {
        return withContext(Dispatchers.IO) {
            remote.recentPlaces()
        }
    }

    override fun getLastUser(): User {
        return _user.value
    }
}