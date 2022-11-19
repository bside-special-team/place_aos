package com.special.place.ui.base

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.special.place.ui.Route
import com.special.place.ui.login.LoginActivity
import com.special.place.ui.login.LoginViewModel
import com.special.place.ui.main.MainActivity
import com.special.place.ui.my.MyInformationActivity
import com.special.place.ui.place.register.PlaceRegisterActivity

open class BaseActivity : ComponentActivity() {
    protected val routeVM: RouteViewModel by viewModels()
    private val loginVM: LoginViewModel by viewModels()
    open val isLoginView: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        routeVM.route.observe(this) {
            when (it) {
                Route.MainPage -> startActivity(MainActivity.newIntent(this))
                Route.MyInfoPage -> startActivity(MyInformationActivity.newIntent(this))
                Route.PlaceRegisterPage -> startActivity(PlaceRegisterActivity.newIntent(this))
                Route.LoginPage -> startActivity(LoginActivity.newIntent(this))
                is Route.PlaceDetailPage -> {
                    it.place
                }
                else -> {}
            }
        }

        if (!isLoginView) {
            loginVM.loginResult.observe(this) {
                Log.d("LoginScreen", "$it")
                if (!it.isLogin) {
                    finish()
                    startActivity(LoginActivity.newIntent(this))
                }
            }
        }
    }
}