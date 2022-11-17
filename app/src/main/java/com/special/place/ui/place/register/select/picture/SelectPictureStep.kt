package com.special.place.ui.place.register.select.picture

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.special.place.resource.R
import com.special.place.ui.theme.Grey200
import com.special.place.ui.theme.Grey600
import com.special.place.ui.theme.Subtitle5
import com.special.place.ui.utils.NextButton
import com.special.place.ui.utils.PrimaryButton
import com.special.place.ui.widget.CenterAlignedTopAppBar

@Composable
fun SelectPictureStep() {
    val imageList: List<String> by mutableStateOf(listOf("", "", "", "", ""))

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = "장소의 사진을 선택해주세요") {

        }
    }) {
        Box(modifier = Modifier.fillMaxSize()) {

            if (imageList.isEmpty()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(Alignment.Center)) {
                    Text(text = "사진을 추가해주세요", style = Subtitle5, color = Grey600)
                    Box(modifier = Modifier.height(24.dp))
                    PrimaryButton(text = "사진 추가", modifier = Modifier.width(108.dp)) {
                        // TODO: 사진 추가
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = 3),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    itemsIndexed(imageList) { index, path ->
                        Box() {
                            Image(painter = painterResource(id = R.drawable.photo), contentDescription = null)
                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .size(16.dp)
                                    .align(Alignment.TopEnd)
                                    .background(color = Grey200, shape = CircleShape)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_close),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(10.dp)
                                )
                            }
                        }
                    }
                }
            }

            NextButton(
                buttonName = "다음 ${imageList.size}/5", clickListener = { /*TODO*/ }, modifier = Modifier
                    .padding(bottom = 24.dp, end = 24.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Preview
@Composable
fun PreviewSelectPictureStep() {
    SelectPictureStep()
}
