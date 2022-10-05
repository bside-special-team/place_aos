package com.special.place.ui.place.register.location

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.special.domain.entities.Coordinate
import com.special.place.R


@Composable
fun DisplayLocation(vm: PlaceLocationEventListener) {
    val displayLocation by vm.displayLocation.observeAsState("")

    Text(displayLocation)
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapView(vm: PlaceLocationEventListener) {
    Box(contentAlignment = Alignment.Center) {
        val cameraState = rememberCameraPositionState()

        NaverMap(
            cameraPositionState = cameraState,
            modifier = Modifier
                .height((LocalConfiguration.current.screenWidthDp * 0.6).dp)
                .fillMaxWidth()
                .padding(16.dp)
        )

        Icon(
            painterResource(id = R.drawable.ic_target_location),
            contentDescription = "target"
        )

        if (cameraState.isMoving.not()) {
            val position = cameraState.position
            vm.updateCameraPosition(Coordinate(latitude = position.target.latitude.toString(), longitude = position.target.longitude.toString()))

            Log.d(
                "CameraState",
                "Lat=${position.target.latitude}, Lng=${position.target.longitude}, ZoomLevel=${position.zoom}"
            )
        }
    }
}