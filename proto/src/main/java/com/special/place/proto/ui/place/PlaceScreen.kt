package com.special.place.proto.ui.place

import android.Manifest
import android.graphics.ImageDecoder
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.*
import com.naver.maps.map.util.FusedLocationSource
import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.Place
import com.special.place.proto.toLatLng
import com.special.place.proto.toMarker
import com.special.place.proto.toast
import kotlinx.coroutines.launch


@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScaffold(
    locationSource: FusedLocationSource,
    list: List<Place>,
    currentLocation: Location? = null
) {
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
                Text("ZoomLevel: ${cameraPosition.position.zoom}")
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
                    Text("", style = TextStyle())
                    Text(
                        text = selectedPlace?.name ?: "TITLE",
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Text(
                    text = "DESCRIPTION",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

//                Text(text = "${selectedPlace?.bestStartTime}에서 ${selectedPlace?.bestEndTime}사이에 방문 하기 좋아요.")

            }
        },
        sheetPeekHeight = 0.dp,
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
            uiSettings = MapUiSettings(isLocationButtonEnabled = true, isScaleBarEnabled = true)
        ) {

            currentLocation?.let {
                CircleOverlay(
                    center = LatLng(it.latitude, it.longitude),
                    radius = 50.0,
                    outlineWidth = 1.dp
                )
            }

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

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun ImageMarkerAppend(
    vm: PlacesViewModel,
    locationSource: FusedLocationSource,
) {
    val context = LocalContext.current
    val cameraState = rememberCameraPositionState()

    if (cameraState.isMoving.not()) {
        val position = cameraState.position
        vm.updateCameraPosition(Coordinate(latitude = position.target.latitude.toString(), longitude = position.target.longitude.toString()))

        Log.d(
            "CameraState",
            "Lat=${position.target.latitude}, Lng=${position.target.longitude}, ZoomLevel=${position.zoom}"
        )
    }

    val imageSelectorLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                val bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
                // vm.updateBitmap(bitmap)
            }
        }

    val requestStoragePermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            if (permissionGranted) {
                imageSelectorLauncher.launch("image/*")
            } else {
                context.toast("이미지 선택을 위해서는 저장소 권한이 필요 합니다.")
            }
        }

    val localMarkers by vm.localMarkers.observeAsState(listOf())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "일상의 발견") }, actions = {
                Text("ZoomLevel: ${cameraState.position.zoom}")
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { requestStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE) }) {
                Icon(Icons.Default.Add, contentDescription = "add_marker")
            }
        }
    ) {
        NaverMap(
            cameraPositionState = cameraState,
            properties = MapProperties(locationTrackingMode = LocationTrackingMode.NoFollow),
            locationSource = locationSource,
            uiSettings = MapUiSettings(isLocationButtonEnabled = true, isScaleBarEnabled = true)
        ) {

            localMarkers.forEach {
                it.toMarker()
            }

        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun LocalMarker.toMarker() = Marker(
    icon = first,
    state = MarkerState(second.toLatLng()),
)