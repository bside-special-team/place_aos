package com.special.place.ui.place.information

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.special.place.resource.R
import com.special.place.ui.my.act.Comment
import com.special.place.ui.my.act.CommentItem
import com.special.place.ui.my.postlist.TagList
import com.special.place.ui.theme.*
import com.special.place.ui.utils.LandMarkProgressBar

@Composable
fun PlaceInfoScreen(
    vm: PlaceDetailViewModel
) {
    val place = vm.placeInfo.observeAsState().value
    val name = place?.name ?: ""
    val type = place?.type ?: ""
    val recommendCnt = place?.recommendCnt ?: 0
    val visitCnt = place?.visitCnt ?: 0
    val writerName = place?.writerName ?: ""
    val hashTags = place?.hashTags ?: listOf("")

    val placeType = if (type == "Hidden") {
        "히든플레이스"
    } else {
        "랜드마크"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(horizontal = 28.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${writerName}님의 발견",
                    color = Purple500,
                    style = Title1,
                    fontSize = 14.sp
                )
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
        Text(text = name, style = Title1)

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = placeType, style = Caption, color = Grey600)
            Spacer(modifier = Modifier.width(12.dp))
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(12.dp)
                    .background(color = Grey600)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp),
                painter = painterResource(R.drawable.ic_eyes_solid),
                contentDescription = "eye"
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "${visitCnt}명", style = Caption, color = Grey600)
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp),
                painter = painterResource(R.drawable.ic_thumbs_up_solid),
                contentDescription = "thumbs_up"
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "${recommendCnt}명", style = Caption, color = Grey600)
        }
        Spacer(modifier = Modifier.height(20.dp))
        TagList(list = hashTags)
        Spacer(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .height(1.dp)
                .background(color = Grey200)
        )
        LandMarkCard(type, recommendCnt)
        Spacer(modifier = Modifier.height(48.dp))
//        CommentScreen()
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "댓글", style = Subtitle4, color = Grey900)

            ClickableText(
                modifier = Modifier
                    .background(color = Grey200, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                text = AnnotatedString("작성하기"),
                style = Subtitle2.copy(fontSize = 14.sp, color = Grey800),
                onClick = {
                    vm.commentBtnClick()
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }

}

@Composable
fun LandMarkCard(type: String, recommend_cnt: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        elevation = 0.dp,
        backgroundColor = Purple500,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (type == "Hidden") {
                Text(
                    text = "랜드마크까지 ${20 - recommend_cnt}표 남았어요!",
                    color = Color.White,
                    style = Subtitle2,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                LandMarkProgressBar(
                    progress = 0.7f,
                    startIcon = R.drawable.ic_hidden_place_purple,
                    endIcon = R.drawable.ic_landmark_purple
                )
            } else {
                Text(
                    text = "랜드마크 달성!",
                    color = Color.White,
                    style = Subtitle2,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painterResource(id = R.drawable.ic_landmark_badge), contentDescription = null,
                    modifier = Modifier
                        .width(56.dp)
                        .height(56.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier
                    .width(312.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(Color.White),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp
                ),
                onClick = { }) {
                Image(
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp),
                    painter = painterResource(R.drawable.ic_thumbs_up_solid),
                    contentDescription = "thumbs_up",
                    colorFilter = ColorFilter.tint(Purple700)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "이 장소를 추천하기", style = Subtitle2, color = Purple700)
            }
        }
    }
}

@Composable
fun CommentScreen(vm: PlaceDetailViewModel) {
    Column() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommentItem(list = arrayListOf(Comment("일상의 발견", "4일전", "정말 좋아요!!!")), index = 0)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun CommentBottomSheet() {
    // TODO 임시
    Log.d("comment", "bototm??")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommentItem(list = arrayListOf(Comment("일상의 발견", "4일전", "정말 좋아요!!!")), index = 0)
        Spacer(modifier = Modifier.height(20.dp))
    }
}