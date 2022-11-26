package com.special.place.ui.place.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow
import com.special.domain.entities.place.Place
import com.special.place.resource.R
import com.special.place.ui.Route
import com.special.place.ui.base.RouteListener
import com.special.place.ui.text
import com.special.place.ui.theme.Grey200
import com.special.place.ui.theme.Grey600
import com.special.place.ui.theme.Grey900
import com.special.place.ui.theme.Purple500
import com.special.place.ui.widget.HashtagChip

@Composable
fun PlaceBottomSheet(
    eventListener: PlaceEventListener,
    routeListener: RouteListener
) {
    val place: Place by eventListener.currentPlace.observeAsState(initial = Place.mock())
    val distance: String by eventListener.distance.observeAsState(initial = "현재 위치를 가져 올 수 없습니다.")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(start = 24.dp, end = 24.dp, top = 32.dp, bottom = 20.dp)
    ) {
        val (typeRef, titleRef, imageRef, hashtagsRef, infoRef, moreButtonRef, visitButtonRef) = createRefs()

        Text(
            place.placeType.text(),
            color = Grey600,
            fontSize = 14.sp,
            modifier = Modifier.constrainAs(typeRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            })

        Text(
            place.name,
            color = Grey900,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(typeRef.bottom)
                start.linkTo(parent.start)
            })

        Box(modifier = Modifier
            .size(48.dp)
            .background(Grey200, shape = RoundedCornerShape(16.dp))
            .constrainAs(imageRef) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }) {
            val firstImageUUID = place.imageUuids.firstOrNull()

            if (firstImageUUID != null) {
                AsyncImage(
                    model = eventListener.coilRequest(firstImageUUID),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_empty_image),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        FlowRow(
            mainAxisSpacing = 6.dp,
            crossAxisSpacing = 6.dp,
            modifier = Modifier.constrainAs(hashtagsRef) {
                linkTo(start = parent.start, end = parent.end)
                top.linkTo(titleRef.bottom, margin = 16.dp)
                width = Dimension.fillToConstraints
            }) {
            place.hashTags.forEach {
                HashtagChip(content = it)
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.constrainAs(infoRef) {
                top.linkTo(hashtagsRef.bottom, margin = 16.dp)
                bottom.linkTo(moreButtonRef.top, margin = 16.dp)
                linkTo(start = parent.start, end = parent.end)
            }) {
            Icon(painterResource(id = R.drawable.ic_place_distance), contentDescription = null)
            Box(modifier = Modifier.width(4.dp))
            Text(distance, color = Grey600, fontSize = 14.sp)
            Box(modifier = Modifier.width(16.dp))
            Icon(painterResource(id = R.drawable.ic_place_visit_point), contentDescription = null)
            Box(modifier = Modifier.width(4.dp))
            Text("250XP", color = Grey600, fontSize = 14.sp)
        }

        Box(modifier = Modifier
            .clickable {
                routeListener.requestRoute(Route.PlaceDetailPage(place = place))
            }
            .height(56.dp)
            .background(Grey200, shape = RoundedCornerShape(20.dp))
            .constrainAs(moreButtonRef) {
                start.linkTo(parent.start)
                end.linkTo(visitButtonRef.start, margin = 10.dp)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }) {
            Text(
                "자세히 보기",
                color = Grey900,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Box(modifier = Modifier
            .clickable {
                eventListener.clickVisitPlace(place.id)
            }
            .height(56.dp)
            .background(Purple500, shape = RoundedCornerShape(20.dp))
            .constrainAs(visitButtonRef) {
                start.linkTo(moreButtonRef.end)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }) {
            Text(
                "방문하기",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}