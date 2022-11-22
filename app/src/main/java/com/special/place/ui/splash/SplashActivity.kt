package com.special.place.ui.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.special.place.ui.login.LoginActivity
import com.special.place.ui.login.LoginViewModel
import com.special.place.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val loginVM: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launchWhenStarted {
            loginVM.loginStatus.collect {
                if (it.isLogin) {
                    startActivity(MainActivity.newIntent(this@SplashActivity))
                    finish()
                } else {
                    startActivity(LoginActivity.newIntent(this@SplashActivity))
                    finish()
                }
            }
        }

    }


}