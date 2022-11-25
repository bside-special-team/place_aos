package com.special.place.ui.base

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.special.place.ui.Route
import com.special.place.ui.login.LoginActivity
import com.special.place.ui.login.LoginViewModel
import com.special.place.ui.main.MainActivity
import com.special.place.ui.my.MyInformationActivity
import com.special.place.ui.place.information.PlaceDetailActivity
import com.special.place.ui.place.register.PlaceRegisterActivity
import kotlinx.coroutines.launch

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
                Route.LoginPage -> startActivity(LoginActivity.newIntent(this))
                Route.Logout -> loginVM.logout()

                is Route.PlaceRegisterPage -> startActivity(
                    PlaceRegisterActivity.newIntent(
                        this,
                        it.location
                    )
                )
                is Route.PlaceDetailPage -> startActivity(
                    PlaceDetailActivity.newIntent(
                        this,
                        it.place
                    )
                )
                else -> {}
            }
        }


        lifecycleScope.launch {
            lifecycleScope.launchWhenResumed {
                loginVM.loginStatus.collect { status ->
                    Log.d("loginBase", "$status by ${this@BaseActivity::class.java.name}")
                    if (!status.isLogin && !isLoginView) {
                        startActivity(LoginActivity.newIntent(this@BaseActivity))
                        finish()
                    } else if (status.isLogin && isLoginView) {
                        startActivity(MainActivity.newIntent(this@BaseActivity))
                        finish()
                    }
                }
            }
        }
    }
}