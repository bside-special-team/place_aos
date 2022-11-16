package com.special.place.ui.place.information

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.special.place.resource.R
import com.special.place.ui.my.act.Comment
import com.special.place.ui.my.act.CommentItem
import com.special.place.ui.my.postlist.TagList
import com.special.place.ui.theme.*
import com.special.place.ui.utils.LandMarkProgressBar

@Preview
@Composable
fun PlaceInfoScreen() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "방방님의 발견", color = Purple500, style = Title1, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Lv2 | 콜럼버스",
                    modifier = Modifier
                        .background(color = Purple100, shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 5.dp),
                    color = Purple500, style = Title1, fontSize = 11.sp
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_bookmark_grey),
                contentDescription = "bookmark"
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = "벚꽃 핀 거리", style = Title1)

        Spacer(modifier = Modifier.height(8.dp))
        Row(
        ) {
            Text(text = "히든플레이스", style = Caption, color = Grey600)
            Spacer(modifier = Modifier.width(12.dp))
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(12.dp)
                    .background(color = Grey600)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "20명", style = Caption, color = Grey600)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "7명", style = Caption, color = Grey600)
        }
        Spacer(modifier = Modifier.height(20.dp))
        TagList(list = listOf("조용한", "아늑한"))
        Spacer(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .height(1.dp)
                .background(color = Grey200)
        )
        LandMarkCard()
        Spacer(modifier = Modifier.height(48.dp))
        CommentScreen()
    }

}

@Composable
fun LandMarkCard() {
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
            Text(
                text = "랜드마크까지 14표 남았어요!",
                color = Color.White,
                style = Subtitle2,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(30.dp))
            LandMarkProgressBar(
                progress = 0.7f,
                startIcon = R.drawable.ic_trophy_star_solid,
                endIcon = R.drawable.ic_info_circle
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier
                    .width(312.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(Color.White),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp
                ),
                onClick = { }) {
                Text(text = "이 장소를 추천하기", style = Subtitle2, color = Purple700)
            }
        }
    }
}

@Composable
fun CommentScreen() {
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "댓글", style = Subtitle4, color = Grey900)
            Card(
                modifier = Modifier
                    .background(color = Grey200, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(text = "작성하기", style = Subtitle2, fontSize = 14.sp, color = Grey800)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        val scrollState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(5) {
                CommentItem(list = arrayListOf(Comment("일상의 발견", "4일전", "정말 좋아요!!!")), index = 0)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}