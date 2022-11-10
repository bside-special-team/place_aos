package com.special.place.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.special.data.usecases.LoginUseCase
import com.special.domain.entities.user.LoginStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    @Inject
    lateinit var loginUseCase: LoginUseCase

    val vm: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LoginScreen(eventListener = loginUseCase) }

        initViewModel()
    }

    private fun initViewModel() {
        vm.loginResult.observe(this) {
            if (it is LoginStatus.LoggedIn) {
                // TODO: 닉네임 입력 화면 or 메인 화면으로 리다이렉트
            } else {
                // TODO: 로그인 실패 팝업 노출
            }
        }
    }

}

