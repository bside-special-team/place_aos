package com.special.place.proto.social

import androidx.activity.ComponentActivity
import com.special.place.proto.social.google.GoogleLogin
import com.special.place.proto.social.kakao.KakaoLogin
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScoped
class LoginFactoryImpl @Inject constructor(
    @ActivityContext activity: ComponentActivity
) : LoginFactory, LoginCallback {
    private val loginMap: Map<LoginType, SocialLogin> = mapOf(
        LoginType.KakaoLoginType to KakaoLogin(activity, this),
        LoginType.GoogleLoginType to GoogleLogin(activity, this),
    )

    override fun doLogin(type: LoginType) {
        loginMap[type]?.doLogin()
    }

    override fun logout(type: LoginType) {
        loginMap[type]?.logout()
    }

    private val _result: MutableSharedFlow<Result<LoginResponse>> = MutableSharedFlow()
    override val result: Flow<Result<LoginResponse>> = _result

    override fun onResponse(loginResponse: LoginResponse) {
        CoroutineScope(Dispatchers.Default).launch {
            _result.emit(Result.success(loginResponse))
        }
    }

    override fun onFailed(error: Throwable) {
        CoroutineScope(Dispatchers.Default).launch {
            _result.emit(Result.failure(error))
        }
    }

}