package com.special.place.ui.place.register.location

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.special.domain.entities.place.Coordinate
import com.special.place.resource.R
import com.special.place.ui.place.register.PlaceRegisterViewModel
import com.special.place.ui.theme.Grey900
import com.special.place.ui.theme.Purple500


@Composable
fun LocationStep(vm: PlaceRegisterViewModel) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (mapRef, addressRef, locationRef, nextRef) = createRefs()

        NaverMapView(vm, modifier = Modifier.constrainAs(mapRef) {
            linkTo(start = parent.start, end = parent.end)
            linkTo(top = parent.top, bottom = parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        })

        DisplayLocation(vm, modifier = Modifier.constrainAs(addressRef) {
            start.linkTo(parent.start, margin = 24.dp)
            end.linkTo(parent.end, margin = 24.dp)
            top.linkTo(parent.top, 20.dp)
            width = Dimension.fillToConstraints
        })

        Icon(painter = painterResource(id = R.drawable.ic_current_location_button),
            contentDescription = "location",
            modifier = Modifier
                .clickable {
//                    vm.updateTrackingMode(LocationTrackingMode.Follow)
                }
                .size(32.dp)
                .constrainAs(locationRef) {
                    start.linkTo(parent.start, margin = 36.dp)
                    bottom.linkTo(parent.bottom, margin = 32.dp)
                })

        Box(modifier = Modifier
            .size(width = 92.dp, height = 56.dp)
            .background(Purple500, shape = RoundedCornerShape(20.dp))
            .constrainAs(nextRef) {
                end.linkTo(parent.end, margin = 24.dp)
                bottom.linkTo(parent.bottom, margin = 24.dp)
            }) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = "다음", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight(700))
                Box(modifier = Modifier.width(4.dp))
                Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.White)
            }

        }
    }
}

@Composable
fun DisplayLocation(vm: PlaceLocationEventListener, modifier: Modifier) {
    val displayLocation by vm.displayLocation.observeAsState("")

    Box(modifier = modifier
        .fillMaxWidth()
        .background(color = Grey900, shape = RoundedCornerShape(20.dp))) {
        Text(displayLocation, color = Color.White, modifier = Modifier
            .padding(vertical = 16.dp)
            .align(Alignment.Center))
    }

}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapView(vm: PlaceLocationEventListener, modifier: Modifier, initialCoordinate: LatLng? = null) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        val cameraState = rememberCameraPositionState(init = {
            if (initialCoordinate != null) {
                position = CameraPosition(initialCoordinate, 17.0)
            }
        })

        NaverMap(
            cameraPositionState = cameraState,
            uiSettings = MapUiSettings(isLocationButtonEnabled = false, isZoomControlEnabled = false),
            modifier = Modifier
                .fillMaxSize()
        )

        Image(painterResource(id = R.drawable.ic_place_normal), contentDescription = "target")

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