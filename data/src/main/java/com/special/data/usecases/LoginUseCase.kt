package com.special.data.usecases

import android.content.Context
import com.special.data.social.LoginCallback
import com.special.data.social.SocialLogin
import com.special.data.social.google.GoogleLogin
import com.special.data.social.kakao.KakaoLogin
import com.special.domain.entities.user.LoginType
import com.special.domain.entities.user.SocialLoginResponse
import com.special.domain.exception.ExceptionListener
import com.special.domain.repositories.UserRepository
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val userRepo: UserRepository,
    @ActivityContext activity: Context,
    private val exceptionListener: ExceptionListener
) : LoginUseCase, LoginCallback {

    private val loginMap: Map<LoginType, SocialLogin> = mapOf(
        LoginType.Kakao to KakaoLogin(activity, this),
        LoginType.Google to GoogleLogin(activity, this),
    )

    override fun onResponse(response: SocialLoginResponse) {
        CoroutineScope(Dispatchers.Default).launch {
            userRepo.socialLogin(response)
        }
    }

    override fun kakaoLogin() {
        runCatching {
            loginMap[LoginType.Kakao]?.doLogin()
        }.onFailure(exceptionListener::updateException)
    }

    override fun googleLogin() {
        runCatching {
            loginMap[LoginType.Google]?.doLogin()
        }.onFailure(exceptionListener::updateException)
    }

    override fun logout() {
        CoroutineScope(Dispatchers.Default).launch {
            userRepo.logout()
            loginMap[LoginType.Google]?.logout()
        }
    }

}

interface LoginUseCase {
    fun kakaoLogin()
    fun googleLogin()

    fun logout()

    companion object {
        fun empty(): LoginUseCase = object : LoginUseCase {
            override fun kakaoLogin() {}

            override fun googleLogin() {}

            override fun logout() {}
        }
    }
}