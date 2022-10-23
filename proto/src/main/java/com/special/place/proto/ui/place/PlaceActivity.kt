package com.special.place.proto.ui.place

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.naver.maps.map.util.FusedLocationSource
import com.special.place.proto.ui.place.register.PlaceRegisterActivity
import com.special.place.proto.ui.theme.PlaceTheme
import dagger.hilt.android.AndroidEntryPoint

const val LOCATION_PERMISSION_REQUEST_CODE = 0xfefefe



@AndroidEntryPoint
class PlaceActivity : ComponentActivity() {
    companion object {
        fun newIntent(context: Context): Intent = Intent(context, PlaceActivity::class.java)
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

    private val locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.initContext(this)

        setContent {
            PlaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    val chargerList by vm.places.observeAsState(listOf())
//                    MainScaffold(locationSource, chargerList)
                    ImageMarkerAppend(vm = vm, locationSource = locationSource)
                }
            }
        }
    }

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