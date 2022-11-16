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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.special.place.R
import com.special.place.ui.theme.Grey100
import com.special.place.ui.theme.Grey600

@Preview
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PlaceImageScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        val slideImage =
            remember { mutableStateOf(com.special.place.resource.R.drawable.ic_trophy_star_solid) }
        val state = rememberPagerState()
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            count = 3, state = state
        ) { page ->
            when (page) {

                0 -> {
                    slideImage.value = R.drawable.ic_siren
                }

                1 -> {
                    slideImage.value = com.special.place.resource.R.drawable.ic_fire_solid
                }

                2 -> {
                    slideImage.value = R.drawable.ic_kakao
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painterResource(slideImage.value),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                )
            }

        }

        Spacer(modifier = Modifier.padding(4.dp))

        DotsIndicator(
            totalDots = 3,
            selectedIndex = state.currentPage,
            selectedColor = Grey600,
            unSelectedColor = Grey100
        )
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
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}