package com.special.place.ui.place.register.complete

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.special.place.resource.R
import com.special.place.ui.theme.Title2
import com.special.place.ui.utils.PrimaryButton
import com.special.place.ui.utils.SecondaryButton

@Composable
fun PlaceRegisterCompleteScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (titleRef, logoRef, placeDetailRef, closeRef) = createRefs()

        Text(text = "장소 작성 완료!", style = Title2, modifier = Modifier.constrainAs(titleRef) {
            linkTo(start = parent.start, end = parent.end)
            top.linkTo(parent.top, margin = 56.dp)
        })

        Image(painter = painterResource(id = R.drawable.logo_complete),
            contentDescription = null,
            modifier = Modifier.constrainAs(logoRef) {
                top.linkTo(titleRef.bottom, margin = 64.dp)
                linkTo(start = parent.start, end = parent.end)
            })

        SecondaryButton("장소 보기", modifier = Modifier.constrainAs(placeDetailRef) {
            start.linkTo(parent.start, margin = 24.dp)
            bottom.linkTo(parent.bottom, margin = 24.dp)
            end.linkTo(closeRef.start, margin = 24.dp)
            width = Dimension.fillToConstraints
        }) {
            //TODO : 플레이스 상세
        }

        PrimaryButton("닫기", modifier = Modifier.constrainAs(closeRef) {
            start.linkTo(placeDetailRef.end)
            bottom.linkTo(parent.bottom, margin = 24.dp)
            end.linkTo(parent.end, margin = 24.dp)
            width = Dimension.fillToConstraints
        }) {
            //TODO : 닫기
        }

    }
}

@Preview
@Composable
fun PreviewPlaceRegisterComplete() {
    PlaceRegisterCompleteScreen()
}