package com.special.place.ui.place.register.location

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
import com.special.place.ui.utils.NextButton
import com.special.place.ui.widget.CenterAlignedTopAppBar
import com.special.place.util.LocationFactory

@Composable
fun LocationStep(vm: PlaceRegisterViewModel, locationFactory: LocationFactory, initialLocation: LatLng? = null) {
    LaunchedEffect(Unit) {
        locationFactory.startLocationTracking()
    }

    DisposableEffect(Unit) {
        onDispose {
            locationFactory.stopLocationTracking()
        }
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = "장소의 위치를 지정해주세요") {

        }
    }) {

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (mapRef, addressRef, nextRef) = createRefs()

            NaverMapView(
                vm,
                locationFactory = locationFactory,
                initialLocation = initialLocation,
                modifier = Modifier.constrainAs(mapRef) {
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

            NextButton("다음", clickListener = {
                vm.next()
            }, modifier = Modifier.constrainAs(nextRef) {
                end.linkTo(parent.end, margin = 24.dp)
                bottom.linkTo(parent.bottom, margin = 24.dp)
            })
        }
    }

}

@Composable
fun DisplayLocation(vm: PlaceLocationEventListener, modifier: Modifier) {
    val displayLocation by vm.displayLocation.observeAsState("")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Grey900, shape = RoundedCornerShape(20.dp))
    ) {
        Text(
            displayLocation, color = Color.White, modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.Center)
        )
    }

}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapView(
    vm: PlaceLocationEventListener,
    modifier: Modifier,
    locationFactory: LocationFactory,
    initialLocation: LatLng? = null
) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        val cameraState = rememberCameraPositionState(init = {
            if (initialLocation != null) {
                position = CameraPosition(initialLocation, 13.6)
            }
        })

        NaverMap(
            cameraPositionState = cameraState,
            uiSettings = MapUiSettings(
                isLocationButtonEnabled = false,
                isZoomControlEnabled = false
            ),
            modifier = Modifier
                .fillMaxSize()
        )

        Image(painterResource(id = R.drawable.ic_place_normal), contentDescription = "target")


        Icon(painter = painterResource(id = R.drawable.ic_current_location_button),
            contentDescription = "location",
            tint = Color.Transparent,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 36.dp, bottom = 32.dp)
                .clickable {
                    val lastLatLng = locationFactory.lastLatLng()
                    if (lastLatLng != null) {
                        cameraState.position = CameraPosition(lastLatLng, 13.6)
                    }

                }
                .size(32.dp))

        if (cameraState.isMoving.not()) {
            val position = cameraState.position
            vm.updateCameraPosition(
                Coordinate(
                    latitude = position.target.latitude.toString(),
                    longitude = position.target.longitude.toString()
                )
            )
        }
    }
}