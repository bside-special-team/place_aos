package com.special.place.proto.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.special.data.social.LoginCallback
import com.special.data.social.SocialLogin
import com.special.domain.entities.user.SocialLoginResponse
import com.special.data.social.google.GoogleLogin
import com.special.data.social.kakao.KakaoLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel(), LoginCallback {

    private val socialLogin: MutableMap<String, SocialLogin> = mutableMapOf()

    fun initLogin(context: Context) {
        socialLogin["kakao"] = KakaoLogin(context, this)

        socialLogin["google"] = GoogleLogin(context, this)
    }


    fun kakaoLogin() {
        socialLogin["kakao"]?.doLogin()
    }

    fun facebookLogin() {
        socialLogin["facebook"]?.doLogin()
    }

    fun googleLogin() {
        socialLogin["google"]?.doLogin()
    }

    fun kakaoLogout() {
        socialLogin["kakao"]?.logout()
    }

    fun facebookLogout() {
        socialLogin["facebook"]?.logout()
    }

    fun googleLogout() {
        socialLogin["google"]?.logout()
    }



    override fun onResponse(response: SocialLoginResponse) {
        println("login status :: $response")
    }

}