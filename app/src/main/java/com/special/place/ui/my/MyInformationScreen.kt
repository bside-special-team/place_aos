package com.special.place.ui.my

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.special.domain.entities.user.LevelInfo
import com.special.place.resource.R
import com.special.place.ui.my.act.MyActActivity
import com.special.place.ui.theme.*

@Composable
fun MyInformationScreen(vm: MyInformationViewModel) {
    val userInfo by vm.userInfo.observeAsState()
    val nextLevel by vm.nextLevel.observeAsState(LevelInfo.none())

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
        BadgeCard(userInfo?.label)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = userInfo?.nickName ?: "", style = Title2)

        Spacer(modifier = Modifier.height(40.dp))

        LevelCard(nextLevel = nextLevel.level, nextLevelPoint = nextLevel.minPoint, myPoint = userInfo?.point ?: 0)

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceBetween
        ) {
            MyButton(
                stringResource(id = R.string.btn_my_act),
                "MyAct",
                R.drawable.ic_fire_solid,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.weight(0.1f))
            MyButton(
                stringResource(id = R.string.btn_my_badge),
                "MyBadge",
                R.drawable.ic_trophy_star_solid,
                modifier = Modifier.weight(1f)
            )
        }

    }
}

@Composable
fun BadgeCard(badge: String?) {
    Text(
        modifier = Modifier
            .background(color = Grey100, shape = RoundedCornerShape(16.dp))
            .padding(10.dp),
        text = badge ?: "",
        style = Title1,
        fontSize = 14.sp
    )
}

@Composable
fun LevelCard(nextLevel: Int, nextLevelPoint: Int, myPoint: Int) {
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
                Text(
                    text = stringResource(id = R.string.txt_next_level_point, nextLevelPoint - myPoint),
                    color = Color.White,
                    style = Title1,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = (nextLevel - 1).toString(),
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
                    progress = myPoint.toFloat() / nextLevelPoint.toFloat(),
                    backgroundColor = Purple300,
                    color = Color.White,
                    modifier = Modifier
                        .height(6.dp)
                        .weight(1f)
                )
                Text(
                    text = nextLevel.toString(),
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
fun MyButton(buttonText: String, intentText: String, icon: Int, modifier: Modifier) {
    val ctx = LocalContext.current

    Button(
        modifier = modifier
            .height(80.dp),
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