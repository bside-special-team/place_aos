package com.special.place.ui.place.information

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PlaceImageScreen(vm: PlaceDetailViewModel) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val imageList: List<String> by vm.imageList.observeAsState(listOf())
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val (imageView, dots) = createRefs()

        val state = rememberPagerState()
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenWidth),
            count = imageList.size, state = state
        ) { page ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .constrainAs(imageView) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.wrapContent
                    }) {
                AsyncImage(
                    model = vm.coilRequest(imageList[page]),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenWidth),
                    contentScale = ContentScale.Crop
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
                totalDots = imageList.size,
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