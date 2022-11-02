package com.special.data.repoimpl

import androidx.activity.ComponentActivity
import com.special.data.social.LoginCallback
import com.special.data.social.LoginFactory
import com.special.data.social.SocialLogin
import com.special.data.social.google.GoogleLogin
import com.special.data.social.kakao.KakaoLogin
import com.special.domain.entities.user.SocialLoginResponse
import com.special.data.utils.PrefsHelper
import com.special.domain.datasources.RemoteDataSource
import com.special.domain.entities.user.LoginStatus
import com.special.domain.entities.user.LoginToken
import com.special.domain.entities.user.LoginType
import com.special.domain.repositories.UserRepository
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val remote: RemoteDataSource,
    private val prefs: PrefsHelper,
    @ActivityContext activity: ComponentActivity
) : UserRepository, LoginCallback {

    private val loginMap: Map<LoginType, SocialLogin> = mapOf(
        LoginType.Kakao to KakaoLogin(activity, this),
        LoginType.Google to GoogleLogin(activity, this),
    )

    val loginStatus: MutableSharedFlow<LoginStatus> = MutableSharedFlow()

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

    override fun onResponse(response: SocialLoginResponse) {
        CoroutineScope(Dispatchers.Default).launch {
            if (response.isLogin && response.idToken != null) {
                val token = remote.socialLogin(response.idToken!!)
                loginStatus.emit(LoginStatus.success(response.type, token))
            } else {
                loginStatus.emit(LoginStatus.empty())
            }
        }
    }

    fun kakaoLogin() {
        loginMap[LoginType.Kakao]?.doLogin()
    }

    fun googleLogin() {
        loginMap[LoginType.Google]?.doLogin()
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