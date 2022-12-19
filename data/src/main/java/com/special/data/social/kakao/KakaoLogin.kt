package com.special.data.social.kakao

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.special.data.social.LoginCallback
import com.special.data.social.SocialLogin
import com.special.domain.entities.user.LoginType
import com.special.domain.entities.user.SocialLoginResponse
import com.special.domain.exception.ExceptionListener

class KakaoLogin constructor(private val context: Context, private val callback: LoginCallback, private val exceptionListener: ExceptionListener) :
    SocialLogin {
    override fun doLogin() {
        kakaoLogin(context) { token, error ->
            error?.let {
                exceptionListener.updateException(it)
            } ?: run {
                exceptionListener.updateMessage("idToken ::: $token")
            }

            token?.idToken?.let { idToken ->
                callback.onResponse(SocialLoginResponse.success(type = LoginType.Kakao, idToken = idToken))
            } ?: run { callback.onResponse(SocialLoginResponse.notLogin()) }
        }
    }

    private fun kakaoLogin(context: Context, callback: (OAuthToken?, Throwable?) -> Unit) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    override fun logout() {
        UserApiClient.instance.logout {
            callback.onResponse(SocialLoginResponse.notLogin())
        }
    }

    override fun checkSigned() {
        doLogin()
    }
}