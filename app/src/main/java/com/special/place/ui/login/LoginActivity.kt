package com.special.place.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.special.data.usecases.LoginUseCase
import com.special.domain.entities.user.LoginStatus
import com.special.place.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    companion object {
        fun newIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

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
            Log.d("LoginScreenLL", "$it")

            if (it is LoginStatus.LoggedIn) {
                // TODO: 닉네임 입력 화면 or 메인 화면으로 리다이렉트


                startActivity(MainActivity.newIntent(this))

            } else {
                Log.d("LoginScreenNN", "$it")
                // TODO: 로그인 실패 팝업 노출
            }
        }
    }

}

