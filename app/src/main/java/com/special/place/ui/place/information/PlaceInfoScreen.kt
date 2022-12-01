package com.special.place.ui.place.information

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.special.domain.entities.place.CommentPlace
import com.special.domain.entities.place.PlaceType
import com.special.place.resource.R
import com.special.place.ui.my.postlist.TagList
import com.special.place.ui.theme.*
import com.special.place.ui.utils.LandMarkProgressBar
import com.special.place.util.DateUtils

@Composable
fun PlaceInfoScreen(
    vm: PlaceDetailListener
) {
    val place by vm.placeInfo.observeAsState()
    val name = place?.name ?: ""
    val type = place?.placeType ?: PlaceType.Hidden
    val recommendCnt = place?.recommendCount ?: 0
    val visitCnt = place?.visitCount ?: 0
    val writerName = place?.nickName ?: "Unknown"
    val hashTags = place?.hashTags ?: listOf()
    val id = place?.id ?: ""

    val placeType = if (type == PlaceType.Hidden) {
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
        LandMarkCard(vm, id, type, recommendCnt)
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
fun LandMarkCard(vm: PlaceDetailListener, id: String, type: PlaceType, recommend_cnt: Int) {
    var progress by remember { mutableStateOf(0.0f) }
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
            if (type == PlaceType.Hidden) {
                Text(
                    text = "랜드마크까지 ${20 - recommend_cnt}표 남았어요!",
                    color = Color.White,
                    style = Subtitle2,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                progress = recommend_cnt.toFloat() / 20
                LandMarkProgressBar(
                    progress = progress,
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
                onClick = { vm.recommendPlace(id) }) {
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
fun CommentList(vm: PlaceDetailListener, comment: CommentPlace) {
    var isDropDownMenu by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .background(color = Grey200, shape = RoundedCornerShape(20.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, top = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = comment.comment.user.nickName + "님", fontSize = 16.sp, color = Grey900,
                style = Subtitle2
            )
            Row(
                modifier = Modifier.padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val commentDay = comment.comment.createdAt
                val text = when (DateUtils.commentTime(commentDay)) {
                    0L -> "오늘"
                    else -> "${DateUtils.commentTime(commentDay)}일 전"
                }
                Text(
                    text = text,
                    color = Grey600,
                    style = Caption
                )
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    modifier = Modifier.clickable { isDropDownMenu = true },
                    painter = painterResource(id = R.drawable.ic_dots), contentDescription = "dots"
                )
                DropdownMenu(
                    expanded = isDropDownMenu,
                    onDismissRequest = { isDropDownMenu = false }) {
                    DropdownMenuItem(onClick = {
                        vm.commentDeleteMenuClick(comment.comment.id)
                        isDropDownMenu = false
                    }) {
                        Text(text = "삭제 요청하기", style = BodyLong2)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.padding(start = 20.dp, top = 16.dp, end = 18.dp, 20.dp),
            text = comment.comment.comment,
            fontSize = 16.sp,
            color = Grey900,
            style = BodyLong2
        )
    }
}