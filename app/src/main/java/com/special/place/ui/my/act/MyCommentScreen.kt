package com.special.place.ui.my.act

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.special.place.resource.R
import com.special.place.ui.my.postlist.PostItem
import com.special.place.ui.my.setting.addFocusCleaner
import com.special.place.ui.theme.Grey200
import com.special.place.ui.theme.Grey600
import com.special.place.ui.theme.Grey900

// 임의 데이터
data class Comment(
    val nickname: String, val date: String, val text: String
)

val commentList = ArrayList<Comment>()

@Preview
@Composable
fun MyCommentScreen() {
    postList.add(MyPostData("히든플레이스", "가로수길 아래", listOf("조용한", "힙한", "신기한"), true))
    commentList.add(Comment("일상의 발견", "4일전", "정말 좋아요!!!"))

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommentPostList(postList, commentList)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CommentPostList(pList: ArrayList<MyPostData>, list: ArrayList<Comment>) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = scrollState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(pList.size) {
            PostItem(pList, 0)
            CommentItem(list, 0)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CommentItem(list: ArrayList<Comment>, index: Int) {

    Column(
        modifier = Modifier
            .background(color = Grey200, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, top = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = list[index].nickname + "님", fontSize = 14.sp, color = Grey900
            )
            Row(
                modifier = Modifier.padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = list[index].date, color = Grey600
                )
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_dots), contentDescription = "dots"
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.padding(start = 20.dp, top = 16.dp, end = 18.dp, 20.dp),
            text = list[index].text,
            fontSize = 18.sp,
            color = Grey900
        )
    }

}
