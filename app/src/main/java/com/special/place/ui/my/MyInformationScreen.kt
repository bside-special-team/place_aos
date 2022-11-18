package com.special.place.ui.my

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.special.place.resource.R
import com.special.place.ui.my.act.MyActActivity
import com.special.place.ui.theme.*

@Preview
@Composable
fun MyInformationScreen() {
    // 임의 데이터
    val name: String = "유저 이름"
    val level: Int = 2
    val point: Int = 250
    val progress: Float = 0.7f
    val badge: String = "외지인"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(topStart = 5.dp))
            .padding(23.5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(18.5.dp))
        // 임시 이미지
        Column(
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .background(color = Grey100, shape = RoundedCornerShape(30.dp))
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,            // crop the image if it's not a square

            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        BadgeCard(badge)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = name, style = Title2)

        Spacer(modifier = Modifier.height(40.dp))

        LevelCard(level, point, progress)

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceBetween
        ) {
            MyButton(stringResource(id = R.string.btn_my_act), "MyAct", R.drawable.ic_fire_solid)
            Spacer(modifier = Modifier.height(20.dp))
            MyButton(
                stringResource(id = R.string.btn_my_badge),
                "MyBadge",
                R.drawable.ic_trophy_star_solid
            )
        }


    }
}

@Composable
fun BadgeCard(badge: String) {
    Text(
        modifier = Modifier
            .background(color = Grey100, shape = RoundedCornerShape(16.dp))
            .padding(10.dp),
        text = badge,
        style = Title1,
        fontSize = 14.sp

    )
}

@Composable
fun LevelCard(level: Int, point: Int, progress: Float) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        elevation = 0.dp,
        backgroundColor = Purple500,

        ) {
        Column(
            modifier = Modifier.padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.txt_current_level),
                    color = Color.White,
                    style = Title1,
                    fontSize = 14.sp
                )
                Row() {
                    Text(
                        text = stringResource(id = R.string.txt_next_level_point, point),
                        color = Color.White,
                        style = Title1,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_info_circle),
                        contentDescription = "info",
                        colorFilter = ColorFilter.tint(Purple100)
                    )
                }

            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = level.toString(),
                    modifier = Modifier
                        .height(28.dp)
                        .width(28.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(16.dp)),
//                        .padding(5.dp, 6.dp, 7.dp, 6.dp),
                    color = Purple700,
                    textAlign = TextAlign.Center,
                    style = Subtitle4
                )
                LinearProgressIndicator(
                    progress = progress,
                    backgroundColor = Purple300,
                    color = Color.White,
                    modifier = Modifier
                        .height(6.dp)
                        .weight(1f)
                )
                Text(
                    text = (level + 1).toString(),
                    modifier = Modifier
                        .height(28.dp)
                        .width(28.dp)
                        .background(color = Purple300, shape = RoundedCornerShape(16.dp)),
//                        .padding(5.dp, 6.dp, 7.dp, 6.dp),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = Subtitle4
                )
            }
        }
    }
}


@Composable
fun MyButton(buttonText: String, intentText: String, icon: Int) {
    val ctx = LocalContext.current

    Button(
        modifier = Modifier
            .height(80.dp)
            .width(146.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(Purple100),
        onClick = {
            if (intentText == "MyAct") {
                val intent = Intent(ctx, MyActActivity::class.java)
                startActivity(ctx, intent, null)
            }
            if (intentText == "MyBadge") {
                // 나의 뱃지
            }
        },
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconResource = painterResource(id = icon)
            Image(
                painter = iconResource,
                contentDescription = "fire"
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = buttonText, color = Purple700, style = Subtitle2)
        }

    }
}