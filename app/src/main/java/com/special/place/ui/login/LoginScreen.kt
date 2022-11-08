package com.special.place.ui.login

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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.special.place.resource.R
import com.special.place.ui.theme.Purple500

@Composable
fun LoginScreen(eventListener: LoginEventListener) {
    ConstraintLayout(
        modifier = Modifier
            .background(Purple500)
            .fillMaxSize()
    ) {
        val (logo, title, kakaoLoginButton, googleLoginButton) = createRefs()

        createVerticalChain(logo, title, chainStyle = ChainStyle.Packed)

        Box(modifier = Modifier
            .size(72.dp)
            .background(Color.White, shape = RoundedCornerShape(24.dp))
            .constrainAs(logo) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = parent.top, bottom = title.top)
            }) {
            Image(
                painter = painterResource(id = R.drawable.icon_foreground),
                contentDescription = "icon",
                colorFilter = ColorFilter.tint(Purple500),
                contentScale = ContentScale.FillWidth
            )
        }

        Text(text = "일상의 발견",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 24.dp)
                .constrainAs(title) {
                    linkTo(start = parent.start, end = parent.end)
                    top.linkTo(logo.bottom)
                    bottom.linkTo(kakaoLoginButton.top)
//                    linkTo(top = logo.bottom, bottom = kakaoLoginButton.top)

                })

        Box(modifier = Modifier
            .clickable {
                eventListener.doKakaoLogin()
            }
            .height(56.dp)
            .background(Color.White.copy(alpha = 0.5F), RoundedCornerShape(20.dp))
            .constrainAs(kakaoLoginButton) {
                start.linkTo(parent.start, 28.dp)
                end.linkTo(parent.end, 28.dp)
                bottom.linkTo(googleLoginButton.top, 20.dp)
                width = Dimension.fillToConstraints
            }) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_kakao_login), contentDescription = "kakao_icon")
                Spacer(modifier = Modifier.width(16.dp))
                Text("카카오톡으로 시작하기")
            }
        }

        Box(modifier = Modifier
            .clickable {
                eventListener.doGoogleLogin()
            }
            .height(56.dp)
            .background(Color.White.copy(alpha = 0.5F), RoundedCornerShape(20.dp))
            .constrainAs(googleLoginButton) {
                start.linkTo(parent.start, 28.dp)
                end.linkTo(parent.end, 28.dp)
                bottom.linkTo(parent.bottom, 32.dp)
                width = Dimension.fillToConstraints
            }) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_google_login), contentDescription = "google_icon")
                Spacer(modifier = Modifier.width(16.dp))
                Text("구글로 시작하기")
            }
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    LoginScreen(LoginEventListener.empty())
}