package com.special.place.proto.ui.place

import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationSource
import com.naver.maps.map.compose.*
import com.special.domain.entities.Place
import com.special.place.proto.toMarker
import kotlinx.coroutines.launch


@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScaffold(
    locationSource: LocationSource,
    list: List<Place>,
    currentLocation: Location? = null,
    registerPlace: (LatLng) -> Unit
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