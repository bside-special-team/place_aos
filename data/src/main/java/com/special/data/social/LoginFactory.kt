package com.special.data.social

import androidx.activity.ComponentActivity
import com.special.data.social.google.GoogleLogin
import com.special.data.social.kakao.KakaoLogin
import com.special.domain.entities.user.LoginType
import com.special.domain.entities.user.SocialLoginResponse
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface LoginFactory {
    fun doLogin(type: LoginType)
    fun logout(type: LoginType)
    val loginStatus: Flow<SocialLoginResponse>
}

@ActivityScoped
class LoginFactoryImpl @Inject constructor(
    @ActivityContext activity: ComponentActivity
) : LoginFactory, LoginCallback {
    private val loginMap: Map<LoginType, SocialLogin> = mapOf(
        LoginType.Kakao to KakaoLogin(activity, this),
        LoginType.Google to GoogleLogin(activity, this),
    )

    override fun doLogin(type: LoginType) {
        loginMap[type]?.doLogin()
    }

    override fun logout(type: LoginType) {
        loginMap[type]?.logout()
    }

    private val _loginStatus: MutableSharedFlow<SocialLoginResponse> = MutableSharedFlow()
    override val loginStatus: Flow<SocialLoginResponse> = _loginStatus

    override fun onResponse(response: SocialLoginResponse) {
        CoroutineScope(Dispatchers.Default).launch {
            _loginStatus.emit(response)
        }
    }

}