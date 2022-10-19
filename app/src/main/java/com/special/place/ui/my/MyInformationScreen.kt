package com.special.place.ui.my

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.special.place.resource.R
import com.special.place.ui.my.act.MyActActivity
import com.special.place.ui.place.register.PlaceRegisterActivity

@Preview
@Composable
fun MyInformationScreen() {

    // 임의 데이터
    val name: String = "유저 이름"
    val level: Int = 7
    val point: Int = 250
    val progress: Float = 0.7f
    val badge: String = "우리동네 백패커"
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
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,            // crop the image if it's not a square
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)                       // clip to the circle shape
                .border(2.dp, Color.Black, CircleShape)   // add a border (optional)
        )
        Spacer(modifier = Modifier.height(10.dp))
        BadgeCard(badge)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = name)

        Spacer(modifier = Modifier.height(40.dp))

        LevelCard(level, point, progress)

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceBetween
        ) {
            MyButton(stringResource(id = R.string.btn_my_act), "MyAct")
            Spacer(modifier = Modifier.height(50.dp))
            MyButton(stringResource(id = R.string.btn_my_badge), "MyBadge")
        }

    }
}

@Composable
fun BadgeCard(badge: String) {
    Card(
        border = BorderStroke(2.dp, Color.Black),
        shape = RoundedCornerShape(25.dp),
        elevation = 5.dp
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = badge
        )
    }
}

@Composable
fun LevelCard(level: Int, point: Int, progress: Float) {
    Card(
        border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,

        ) {
        Column(
            modifier = Modifier.padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Row() {
                Text(text = stringResource(id = R.string.txt_current_level, level))
                Spacer(modifier = Modifier.width(30.dp))
                Text(text = stringResource(id = R.string.txt_next_level_point, point))
            }
            Spacer(modifier = Modifier.height(30.dp))
            LinearProgressIndicator(progress = progress)
        }
    }
}


@Composable
fun MyButton(buttonText: String, intentText:String) {
    val ctx = LocalContext.current

    Button(
        border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier
            .height(90.dp)
            .width(150.dp),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        onClick = {
            if(intentText=="MyAct"){
                val intent = Intent(ctx, MyActActivity::class.java)
                startActivity(ctx, intent, null)
            }
            if(intentText=="MyBadge"){

            }

        }
    ) {
        Text(text = buttonText)
    }
}