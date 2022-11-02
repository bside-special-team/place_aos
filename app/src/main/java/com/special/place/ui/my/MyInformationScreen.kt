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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.special.place.resource.R
import com.special.place.ui.my.act.MyActActivity
import com.special.place.ui.theme.Grey100
import com.special.place.ui.theme.Purple100
import com.special.place.ui.theme.Purple700

@Preview
@Composable
fun MyInformationScreen() {
    // 임의 데이터
    val name: String = "유저 이름"
    val level: Int = 7
    val point: Int = 250
    val progress: Float = 0.7f
    val badge: String = "외지인"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        Text(text = name)

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
            .background(color = Grey100, shape = RoundedCornerShape(12.dp))
            .padding(10.dp),
        text = badge

    )
}

@Composable
fun LevelCard(level: Int, point: Int, progress: Float) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        elevation = 5.dp,

        ) {
        Column(
            modifier = Modifier.padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Row() {
                Text(text = stringResource(id = R.string.txt_current_level))
                Spacer(modifier = Modifier.width(30.dp))
                Text(text = stringResource(id = R.string.txt_next_level_point, point))
            }
            Spacer(modifier = Modifier.height(30.dp))
            LinearProgressIndicator(progress = progress)
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

            }
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconResource = painterResource(id = icon)
            Image(
                painter = iconResource,
                contentDescription = "fire"
            )
            Text(text = buttonText, color = Purple700)
        }

    }
}