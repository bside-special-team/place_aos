package com.special.place.proto.ui.login

import android.app.Activity
import android.content.Context
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.lifecycle.ViewModel
import com.special.place.proto.social.LoginCallback
import com.special.place.proto.social.LoginResponse
import com.special.place.proto.social.SocialLogin
import com.special.place.proto.social.fb.FaceBookLogin
import com.special.place.proto.social.google.GoogleLogin
import com.special.place.proto.social.kakao.KakaoLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel(), LoginCallback {

    private val socialLogin: MutableMap<String, SocialLogin> = mutableMapOf()

    fun initLogin(context: Context) {
        socialLogin["kakao"] = KakaoLogin(context, this)

        (context as? ActivityResultRegistryOwner)?.let { activity ->
            socialLogin["facebook"] = FaceBookLogin(activity, this)
        }

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



    override fun onResponse(loginResponse: LoginResponse) {
        println("login result :: $loginResponse")
    }

    override fun onFailed(error: Throwable) {
        error.printStackTrace()
    }
}