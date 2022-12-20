package com.special.place.ui.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.special.data.utils.PrefsHelper
import com.special.place.resource.R
import com.special.place.ui.login.LoginActivity
import com.special.place.ui.login.LoginViewModel
import com.special.place.ui.main.MainActivity
import com.special.place.ui.theme.Purple700
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val loginVM: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen()
        }
        initViewModel()
    }

    private fun initViewModel() {
        if (PrefsHelper(this@SplashActivity).shownOnBoarding) {
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
        } else {
            startActivity(OnBoardingActivity.newIntent(this@SplashActivity))
            finish()
        }

    }
}

@Preview
@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple700)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}