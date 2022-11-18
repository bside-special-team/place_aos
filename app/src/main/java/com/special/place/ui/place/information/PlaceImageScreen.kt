package com.special.place.ui.place.information

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Preview
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PlaceImageScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val (imageView, dots) = createRefs()

        val slideImage =
            remember { mutableStateOf(com.special.place.resource.R.drawable.image) }
        val state = rememberPagerState()
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenWidth),
            count = 3, state = state
        ) { page ->
            when (page) {

                0 -> {
                    slideImage.value = com.special.place.resource.R.drawable.image
                }

                1 -> {
                    slideImage.value = com.special.place.resource.R.drawable.image
                }

                2 -> {
                    slideImage.value = com.special.place.resource.R.drawable.image
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .constrainAs(imageView) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.wrapContent
                    }) {
                Image(
                    painterResource(slideImage.value),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenWidth),
                )
            }

        }
        Column(modifier = Modifier
            .constrainAs(dots) {
                start.linkTo(parent.start)
                top.linkTo(imageView.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
            .padding(vertical = 52.dp)) {
            DotsIndicator(
                totalDots = 3,
                selectedIndex = state.currentPage,
                selectedColor = Color.White,
                unSelectedColor = Color.White
            )
        }

    }


}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color,
) {
    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()

    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor.copy(alpha = 0.4f))
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 3.dp))
            }
        }
    }
}