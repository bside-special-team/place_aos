package com.special.place.ui.place.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.flowlayout.FlowRow
import com.special.domain.entities.place.Place
import com.special.place.resource.R
import com.special.place.ui.Route
import com.special.place.ui.base.RouteListener
import com.special.place.ui.theme.Grey200
import com.special.place.ui.theme.Grey600
import com.special.place.ui.theme.Grey900
import com.special.place.ui.theme.Purple500
import com.special.place.ui.widget.HashtagChip

@Composable
fun PlaceBottomSheet(
    place: Place,
    eventListener: PlaceEventListener,
    routeListener: RouteListener
) {
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
            Image(
                painter = painterResource(id = R.drawable.ic_empty_image),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        FlowRow(
            mainAxisSpacing = 6.dp,
            crossAxisSpacing = 6.dp,
            modifier = Modifier.constrainAs(hashtags) {
                linkTo(start = parent.start, end = parent.end)
                top.linkTo(title.bottom, margin = 16.dp)
                width = Dimension.fillToConstraints
            }) {
            listOf(
                "\uD83E\uDD2B 조용한",
                "\uD83D\uDE0E 힙한",
                "⚡ 신비한",
                "\uD83D\uDE06 재밌는",
                "\uD83E\uDD44 음식점"
            ).forEach() {
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
            Text(
                "자세히 보기",
                color = Grey900,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Center)
            )
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
            Text(
                "방문하기",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}