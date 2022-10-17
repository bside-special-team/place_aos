package com.special.place.proto.social.kakao

import android.content.Context
import com.special.place.proto.social.LoginCallback
import com.special.place.proto.social.SocialLogin

class KakaoLogin constructor(private val context: Context, private val callback: LoginCallback): SocialLogin {
    override fun doLogin() {
        kakaoLogin(context) { token, error ->
            token?.let {
                UserApiClient.instance.me { user, error ->
                    user?.let { callback.onResponse(KakaoLoginResponse(user)) } ?: run {
                        callback.onFailed(error!!)
                    }
                }
            } ?: run { callback.onFailed(error!!) }
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

    }
}