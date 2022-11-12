package com.special.place.ui.place.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.google.accompanist.flowlayout.FlowRow
import com.naver.maps.map.LocationSource
import com.naver.maps.map.compose.LocationTrackingMode
import com.special.domain.entities.place.Place
import com.special.place.base.RouteListener
import com.special.place.resource.R
import com.special.place.ui.Route
import com.special.place.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlaceScreen(
    locationSource: LocationSource,
    eventListener: PlaceEventListener,
    routeListener: RouteListener,
) {

    val placeCount by eventListener.hiddenPlaceCount.observeAsState(initial = 0)
    val landmarkCount by eventListener.landmarkCount.observeAsState(initial = 0)

    val coroutineScope = rememberCoroutineScope()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = { PlaceBottomSheet(Place.mock(), eventListener, routeListener) },
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
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
                .background(brush = Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.2f), Color.White)))
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
                }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(painterResource(id = R.drawable.ic_tour_start), contentDescription = "tour", tint = Color.White)
                    Box(modifier = Modifier.height(4.dp))
                    Text(text = "시작", color = Color.White)
                }
            }

            /* 내 정보 버튼 */
            Box(modifier = Modifier
                .clickable {
                    //TODO : 내 정보 화면 이동
                    routeListener.requestRoute(Route.MyInfoPage)
                }
                .size(58.dp)
                .background(color = Grey900, shape = RoundedCornerShape(18.dp))
                .constrainAs(myInfoButton) {
                    end.linkTo(tourButton.start, margin = 20.dp)
                    linkTo(top = tourButton.top, bottom = tourButton.bottom)
                }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(painterResource(id = R.drawable.ic_my_info), contentDescription = "tour", tint = Color.White)
                    Box(modifier = Modifier.height(4.dp))
                    Text(text = "내 정보", color = Grey500, fontSize = 10.sp)
                }
            }

            /* 플레이스 작성 버튼 */
            Box(modifier = Modifier
                .clickable {
                    //TODO : 플레이스 작성 화면 이동
                    routeListener.requestRoute(Route.PlaceRegisterPage)
                }
                .size(58.dp)
                .background(color = Grey900, shape = RoundedCornerShape(18.dp))
                .constrainAs(registerButton) {
                    start.linkTo(tourButton.end, margin = 20.dp)
                    linkTo(top = tourButton.top, bottom = tourButton.bottom)
                }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(painterResource(id = R.drawable.ic_place_register), contentDescription = "tour", tint = Color.White)
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
                        tint = Grey500,
                        modifier = Modifier.size(width = 12.dp, height = 15.dp)
                    )
                    Box(modifier = Modifier.width(4.dp))
                    Text("$placeCount", color = Grey400, fontSize = 11.sp)
                    Box(modifier = Modifier.width(16.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_landmark_count),
                        contentDescription = null,
                        tint = Grey500,
                        modifier = Modifier.size(width = 12.dp, height = 15.dp)
                    )
                    Box(modifier = Modifier.width(4.dp))
                    Text("$landmarkCount", color = Grey400, fontSize = 11.sp)
                }
            }

            /* 사용방법 버튼 */
            Box(modifier = Modifier
                .clickable {
                    //TODO: 가이드 화면 노출 (온보딩??)
                    routeListener.requestRoute(Route.TutorialPage)
                }
                .background(Grey900, shape = CircleShape)
                .size(36.dp)
                .constrainAs(guideButton) {
                    end.linkTo(parent.end, margin = 24.dp)
                    linkTo(top = placeCountRef.top, bottom = placeCountRef.bottom)
                }) {
                Icon(
                    painterResource(id = R.drawable.ic_guide_button), contentDescription = "guide", tint = Grey500, modifier = Modifier
                        .align(Alignment.Center)
                        .size(16.dp)
                )
            }

            Icon(painter = painterResource(id = R.drawable.ic_location_crosshair), contentDescription = "location", tint = Grey500, modifier = Modifier
                .clickable {
                    eventListener.updateTrackingMode(LocationTrackingMode.Follow)
                }
                .size(32.dp)
                .constrainAs(myLocationButton) {
                    bottom.linkTo(tourButton.top, margin = 24.dp)
                    linkTo(parent.start, parent.end)
                })

        }

    }


}


@Composable
fun PlaceBottomSheet(place: Place, eventListener: PlaceEventListener, routeListener: RouteListener) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(start = 24.dp, end = 24.dp, top = 32.dp, bottom = 20.dp)
    ) {
        val (type, title, image, hashtags, info, moreButton, visitButton) = createRefs()

        Text("히든플레이스", color = Grey600, fontSize = 14.sp, modifier = Modifier.constrainAs(type) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        })

        Text("성수동 카페", color = Grey900, fontSize = 20.sp, modifier = Modifier.constrainAs(title) {
            top.linkTo(type.bottom)
            start.linkTo(parent.start)
        })

        Box(modifier = Modifier
            .size(48.dp)
            .background(Grey200, shape = RoundedCornerShape(16.dp))
            .constrainAs(image) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }) {
            Image(painter = painterResource(id = R.drawable.ic_empty_image), contentDescription = null, modifier = Modifier.align(Alignment.Center))
        }

        FlowRow(
            mainAxisSpacing = 6.dp,
            crossAxisSpacing = 6.dp,
            modifier = Modifier.constrainAs(hashtags) {
                linkTo(start = parent.start, end = parent.end)
                top.linkTo(title.bottom, margin = 16.dp)
                width = Dimension.fillToConstraints
            }) {
            listOf("\uD83E\uDD2B 조용한", "\uD83D\uDE0E 힙한", "⚡ 신비한", "\uD83D\uDE06 재밌는", "\uD83E\uDD44 음식점").forEach() {
                HashtagChip(content = it)
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.constrainAs(info) {
                top.linkTo(hashtags.bottom, margin = 16.dp)
                bottom.linkTo(moreButton.top, margin = 16.dp)
                linkTo(start = parent.start, end = parent.end)
            }) {
            Icon(painterResource(id = R.drawable.ic_place_distance), contentDescription = null)
            Box(modifier = Modifier.width(4.dp))
            Text("50m", color = Grey600, fontSize = 14.sp)
            Box(modifier = Modifier.width(16.dp))
            Icon(painterResource(id = R.drawable.ic_place_visit_point), contentDescription = null)
            Box(modifier = Modifier.width(4.dp))
            Text("250XP", color = Grey600, fontSize = 14.sp)
        }

        Box(modifier = Modifier
            .clickable {
                // TODO 자세히 보기
                routeListener.requestRoute(Route.PlaceDetailPage(place = place))
            }
            .height(56.dp)
            .background(Grey200, shape = RoundedCornerShape(20.dp))
            .constrainAs(moreButton) {
                start.linkTo(parent.start)
                end.linkTo(visitButton.start, margin = 10.dp)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }) {
            Text("자세히 보기", color = Grey900, fontSize = 16.sp, modifier = Modifier.align(Alignment.Center))
        }

        Box(modifier = Modifier
            .clickable {
                // TODO 방문 하기
                eventListener.clickVisitPlace(place.id)
            }
            .height(56.dp)
            .background(Purple500, shape = RoundedCornerShape(20.dp))
            .constrainAs(visitButton) {
                start.linkTo(moreButton.end)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }) {
            Text("방문하기", color = Color.White, fontSize = 16.sp, modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun HashtagChip(content: String) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .border(1.dp, Grey200, RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Text(content, color = Grey800, fontSize = 13.sp, modifier = Modifier.align(Alignment.Center))
    }
}