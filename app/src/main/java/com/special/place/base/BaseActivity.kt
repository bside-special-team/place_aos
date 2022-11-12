package com.special.place.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.special.place.ui.Route
import com.special.place.ui.main.MainActivity

open class BaseActivity : ComponentActivity() {
    protected val routeVM: RouteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        routeVM.route.observe(this) {
            when (it) {
                Route.MainPage -> {
                    startActivity(MainActivity.newIntent(this))
                }
                Route.MyInfoPage -> {
                    // TODO: 마이 페이지 연결
                }
                Route.PlaceRegisterPage -> {}
                is Route.PlaceDetailPage -> {
                    it.place
                }
                else -> {}
            }
        }
    }
}