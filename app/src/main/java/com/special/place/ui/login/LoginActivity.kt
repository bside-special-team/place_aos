package com.special.place.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.special.data.usecases.LoginUseCase
import com.special.place.ui.base.BaseActivity
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
    }

    override val isLoginView: Boolean = true
}

