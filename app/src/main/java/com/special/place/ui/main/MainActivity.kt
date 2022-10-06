package com.special.place.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationSource
import com.naver.maps.map.compose.*
import com.naver.maps.map.util.FusedLocationSource
import com.special.domain.entities.Place
import com.special.place.toMarker
import com.special.place.ui.place.register.PlaceRegisterActivity
import com.special.place.ui.theme.PlaceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

const val LOCATION_PERMISSION_REQUEST_CODE = 0xfefefe

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
        setContent {
            PlaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val chargerList by vm.places.observeAsState(listOf())
                    MainScaffold(locationSource, chargerList) {
                        startActivity(PlaceRegisterActivity.newIntent(this))
                    }
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

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScaffold(locationSource: LocationSource, list: List<Place>, registerPlace: () -> Unit) {
    val cameraPosition = rememberCameraPositionState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    var selectedPlace by remember { mutableStateOf<Place?>(null) }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        topBar = {
            TopAppBar(title = { Text(text = "일상의 발견") }, actions = {
                IconButton(onClick = registerPlace) {
                    Icon(Icons.Outlined.Add, contentDescription = "add")
                }
            })
        },
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(200.dp)
            ) {
                Row(modifier = Modifier.padding(bottom = 16.dp)) {
                    Text(selectedPlace?.category?.name ?: "", style = TextStyle())
                    Text(
                        text = selectedPlace?.name ?: "TITLE",
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Text(
                    text = selectedPlace?.description ?: "DESCRIPTION",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(text = "${selectedPlace?.bestStartTime}에서 ${selectedPlace?.bestEndTime}사이에 방문 하기 좋아요.")

            }
        },
        sheetPeekHeight = 40.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetElevation = 5.dp
    ) {
        NaverMap(
            cameraPositionState = cameraPosition,
            onMapClick = { _, _ ->
                coroutineScope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            },
            properties = MapProperties(locationTrackingMode = LocationTrackingMode.NoFollow),
            locationSource = locationSource,
            uiSettings = MapUiSettings(isLocationButtonEnabled = true)
        ) {
            list.forEach {
                it.toMarker {
                    selectedPlace = it

                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }

                }
            }
        }
    }
}