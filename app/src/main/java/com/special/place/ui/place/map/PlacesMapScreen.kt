package com.special.place.ui.place.map

import android.util.Log
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationSource
import com.naver.maps.map.NaverMap
import com.naver.maps.map.compose.*
import com.special.domain.entities.place.Place
import com.special.place.toMarker
import kotlinx.coroutines.launch

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterialApi::class)
@Composable
fun PlacesMapScreen(
    locationSource: LocationSource,
    eventListener: PlaceEventListener,
    bottomSheetState: BottomSheetState,
    modifier: Modifier
) {
    val cameraPosition = rememberCameraPositionState(init = {
        position = CameraPosition(
            NaverMap.DEFAULT_CAMERA_POSITION.target,
            13.6
        )
    })

    val places by eventListener.places.observeAsState(initial = listOf())

    val coroutineScope = rememberCoroutineScope()

    var selectedPlace by remember { mutableStateOf<Place?>(null) }

    val trackingMode by eventListener.trackingMode.observeAsState(LocationTrackingMode.Follow)

    NaverMap(
        cameraPositionState = cameraPosition,
        onMapClick = { _, _ ->
            eventListener.updateTrackingMode(LocationTrackingMode.NoFollow)

            coroutineScope.launch {

                if (bottomSheetState.isExpanded) {
                    bottomSheetState.collapse()
                }
            }
        },
        properties = MapProperties(locationTrackingMode = trackingMode),
        locationSource = locationSource,
        onLocationChange = { location ->
            eventListener.updateCurrentLocation(location)
        },
        uiSettings = MapUiSettings(isLocationButtonEnabled = false, isScaleBarEnabled = false, isZoomControlEnabled = false, isLogoClickEnabled = false),
        modifier = modifier
    ) {
        places.forEach {
            it.toMarker {
                selectedPlace = it

                coroutineScope.launch {
                    if (bottomSheetState.isCollapsed) {
                        bottomSheetState.expand()
                    } else {
                        bottomSheetState.collapse()
                    }
                }

            }
        }
    }

    if (cameraPosition.isMoving.not()) {
        // TODO: 이벤트 수신이 계속 되고 있음.

        val position = cameraPosition.position
        position.let { eventListener.updateCameraPosition(it) }

        Log.d(
            "CameraState",
            "Lat=${position.target.latitude}, Lng=${position.target.longitude}, ZoomLevel=${position.zoom}"
        )
    }
}