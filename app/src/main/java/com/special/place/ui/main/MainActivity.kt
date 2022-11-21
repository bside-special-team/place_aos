package com.special.place.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationSource
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.special.place.ui.base.BaseActivity
import com.special.place.ui.base.RouteListener
import com.special.place.ui.place.map.PlaceEventListener
import com.special.place.ui.place.map.PlaceScreen
import com.special.place.ui.place.register.PlaceRegisterActivity
import com.special.place.ui.theme.PlaceTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    companion object {
        fun newIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    private val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.all { (_, granted) -> granted }) {
                //TODO: 퍼미션 수락된 상태
            } else {
                showPermissionFailedPopup()
            }
        }

    private val vm: PlacesViewModel by viewModels()

    @Inject
    lateinit var locationSource: LocationSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            PlaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    MainScaffold(locationSource, vm, routeVM) {
                        startActivity(PlaceRegisterActivity.newIntent(this))

                    }
                }

            }
        }
    }

    val onClick = { /* Do something */ }

    override fun onStart() {
        super.onStart()
        checkPermission()
    }

    private fun checkPermission() {
        if (permissions.all {
                ContextCompat.checkSelfPermission(
                    this,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            // TODO: 권한 수락된 상태!
        } else {
            requestPermission.launch(permissions)
        }
    }

    private fun showPermissionFailedPopup() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setCancelable(false)
            .setMessage("위치 권한이 필요합니다.")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                requestPermission.launch(permissions)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                finish()
            }
            .show()
    }
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScaffold(
    locationSource: LocationSource,
    eventListener: PlaceEventListener,
    routeListener: RouteListener,
    registerPlace: (LatLng) -> Unit
) {
    PlaceScreen(
        locationSource = locationSource,
        eventListener = eventListener,
        routeListener = routeListener
    )

}
