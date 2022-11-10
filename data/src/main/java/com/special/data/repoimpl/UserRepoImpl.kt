package com.special.data.repoimpl

import com.special.data.utils.PrefsHelper
import com.special.domain.datasources.RemoteDataSource
import com.special.domain.entities.user.LoginStatus
import com.special.domain.entities.user.LoginToken
import com.special.domain.entities.user.SocialLoginResponse
import com.special.domain.entities.user.badge.Badge
import com.special.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val remote: RemoteDataSource,
    private val prefs: PrefsHelper
) : UserRepository {

    override val loginStatus: MutableSharedFlow<LoginStatus> = MutableSharedFlow()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            if (!prefs.isLogin) {
                loginStatus.emit(LoginStatus.empty())
            } else {
                // TODO: accessToken 갱신
                loginStatus.emit(LoginStatus.success(prefs.loginType, loadToken()))
            }
        }
    }

    override suspend fun socialLogin(response: SocialLoginResponse) {
        CoroutineScope(Dispatchers.Default).launch {
            if (response.isLogin && response.idToken != null) {
                val token = remote.socialLogin(response.idToken!!)
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
        val accessToken = prefs.accessToken
        val refreshToken = prefs.refreshToken

        return if (accessToken != null && refreshToken != null) {
            LoginToken(accessToken, refreshToken)
        } else {
            // TODO: EmptyTokenException 생성
            throw IllegalStateException()
        }
    }


}