package com.special.place.ui.place.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import com.naver.maps.map.LocationSource
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.util.FusedLocationSource
import com.special.place.resource.R
import com.special.place.ui.Route
import com.special.place.ui.base.RouteListener
import com.special.place.ui.main.toLatLnt
import com.special.place.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlaceScreen(
    locationSource: LocationSource,
    eventListener: PlaceEventListener,
    routeListener: RouteListener,
) {
    val myLocationVisible by eventListener.visibleCurrentLocationButton.observeAsState(false)
    val placeCount by eventListener.hiddenPlaceCount.observeAsState(initial = 0)
    val landmarkCount by eventListener.landmarkCount.observeAsState(initial = 0)

    val coroutineScope = rememberCoroutineScope()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = { PlaceBottomSheet(eventListener, routeListener) },
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        sheetPeekHeight = 0.dp,
        sheetElevation = 5.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxSize()
        ) {
            val (map, bottomBackground, tourButton, myInfoButton, registerButton, myLocationButton, placeCountRef, guideButton) = createRefs()

            PlacesMapScreen(
                locationSource = locationSource,
                eventListener = eventListener,
                bottomSheetState = bottomSheetScaffoldState.bottomSheetState,
                modifier = Modifier.constrainAs(map) {
                    linkTo(start = parent.start, end = parent.end)
                    linkTo(top = parent.top, bottom = parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                })

            /* 하단 그라디언트 배경 */
            Box(modifier = Modifier
                .height(218.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.White.copy(alpha = 0.0f),
                            Color.White.copy(alpha = 0.2f),
                            Color.White
                        )
                    )
                )
                .constrainAs(bottomBackground) {
                    linkTo(start = parent.start, end = parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                })

            /* 여정(산책) 시작 버튼 */
            Box(modifier = Modifier
                .clickable {
                    //TODO : 여정 시작 상태로
                    eventListener.clickTourStart()

                    coroutineScope.launch {

                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }
                }
                .size(80.dp)
                .background(color = Purple500, shape = RoundedCornerShape(26.dp))
                .constrainAs(tourButton) {
                    linkTo(start = parent.start, end = parent.end)
                    bottom.linkTo(parent.bottom, 32.dp)

                    visibility = Visibility.Gone
                }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_tour_start),
                        contentDescription = "tour",
                        tint = Color.White
                    )
                    Box(modifier = Modifier.height(4.dp))
                    Text(text = "시작", color = Color.White)
                }
            }

            /* 내 정보 버튼 */
            Box(modifier = Modifier
                .clickable { routeListener.requestRoute(Route.MyInfoPage) }
                .size(58.dp)
                .background(color = Grey900, shape = RoundedCornerShape(18.dp))
                .constrainAs(myInfoButton) {
                    end.linkTo(tourButton.start, margin = 20.dp, goneMargin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 44.dp)
                }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_my_info),
                        contentDescription = "tour",
                        tint = Color.White
                    )
                    Box(modifier = Modifier.height(4.dp))
                    Text(text = "내 정보", color = Grey500, fontSize = 10.sp)
                }
            }

            /* 플레이스 작성 버튼 */
            Box(modifier = Modifier
                .clickable {
                    val lastLocation = (locationSource as? FusedLocationSource)?.lastLocation?.toLatLnt()
                    routeListener.requestRoute(
                        Route.PlaceRegisterPage(
                            lastLocation
                        )
                    )
                }
                .size(58.dp)
                .background(color = Grey900, shape = RoundedCornerShape(18.dp))
                .constrainAs(registerButton) {
                    start.linkTo(tourButton.end, margin = 20.dp, goneMargin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 44.dp)
                }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_place_register),
                        contentDescription = "tour",
                        tint = Color.White
                    )
                    Box(modifier = Modifier.height(4.dp))
                    Text(text = "작성", color = Grey500, fontSize = 10.sp)
                }
            }

            /* 주변 히든플레이스, 랜드마크 카운팅 */
            Box(modifier = Modifier
                .size(width = 116.dp, height = 40.dp)
                .background(Grey900, shape = RoundedCornerShape(20.dp))
                .constrainAs(placeCountRef) {
                    linkTo(start = parent.start, end = parent.end)
                    top.linkTo(parent.top, margin = 44.dp)
                }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_place_count),
                        contentDescription = null,
                        tint = Grey500
                    )
                    Box(modifier = Modifier.width(4.dp))
                    Text("$placeCount", color = Grey400, fontSize = 11.sp)
                    Box(modifier = Modifier.width(16.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_landmark_count),
                        contentDescription = null,
                        tint = Grey500
                    )
                    Box(modifier = Modifier.width(4.dp))
                    Text("$landmarkCount", color = Grey400, fontSize = 11.sp)
                }
            }

            /* 사용방법 버튼 */
            Box(modifier = Modifier
                .clickable {
                    //TODO: 가이드 화면 노출 (온보딩??)

                    routeListener.requestRoute(Route.Logout)
                }
                .background(Grey900, shape = CircleShape)
                .size(36.dp)
                .constrainAs(guideButton) {
                    end.linkTo(parent.end, margin = 24.dp)
                    linkTo(top = placeCountRef.top, bottom = placeCountRef.bottom)
                }) {
                Icon(
                    painterResource(id = R.drawable.ic_guide_button),
                    contentDescription = "guide",
                    tint = Grey500,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(16.dp)
                )
            }

            if (myLocationVisible) {
                Icon(painter = painterResource(id = R.drawable.ic_current_location_button),
                    contentDescription = "location",
                    modifier = Modifier
                        .clickable {
                            eventListener.updateTrackingMode(LocationTrackingMode.Follow)
                        }
                        .size(32.dp)
                        .constrainAs(myLocationButton) {
                            bottom.linkTo(myInfoButton.top, margin = 35.dp)
                            linkTo(parent.start, parent.end)
                        })
            }

        }

    }


}
