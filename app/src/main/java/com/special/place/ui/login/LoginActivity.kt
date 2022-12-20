package com.special.place.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.special.data.usecases.LoginUseCase
import com.special.place.ui.base.BaseActivity
import com.special.place.ui.main.MainActivity
import com.special.place.ui.my.setting.nickname.modify.NicknameModifyActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    companion object {
        fun newIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    @Inject
    lateinit var loginUseCase: LoginUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LoginScreen(eventListener = loginUseCase) }

        lifecycleScope.launchWhenResumed {
            loginVM.loginStatus.collect { status ->
                if (status.isLogin && isLoginView) {
                    startActivity(MainActivity.newIntent(this@LoginActivity))
                    finish()
                }

                if (status.isLogin && isLoginView && loginVM.getNickname() == null) {
                    startActivity(NicknameModifyActivity.newIntent(this@LoginActivity))
                    finish()
                }

                if (!status.isLogin && isLoginView) {
                    FirebaseCrashlytics.getInstance().log("LoginFailed $status")

//                    AlertDialog.Builder(this@LoginActivity)
//                        .setTitle("로그인에 실패 하였습니다.")
//                        .setPositiveButton("확인") { _, _ -> }
//                        .create().show()
                }
            }
        }
    }

    override val isLoginView: Boolean = true
}

