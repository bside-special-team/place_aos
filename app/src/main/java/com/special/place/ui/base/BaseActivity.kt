package com.special.place.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.special.place.ui.Route
import com.special.place.ui.login.LoginActivity
import com.special.place.ui.main.MainActivity
import com.special.place.ui.place.register.PlaceRegisterActivity

open class BaseActivity : ComponentActivity() {
    protected val routeVM: RouteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        routeVM.route.observe(this) {
            when (it) {
                Route.MainPage -> startActivity(MainActivity.newIntent(this))
                Route.MyInfoPage -> {
                    // TODO: 마이 페이지 연결
                }
                Route.PlaceRegisterPage -> startActivity(PlaceRegisterActivity.newIntent(this))
                Route.LoginPage -> startActivity(LoginActivity.newIntent(this))
                is Route.PlaceDetailPage -> {
                    it.place
                }
                else -> {}
            }
        }
    }
}