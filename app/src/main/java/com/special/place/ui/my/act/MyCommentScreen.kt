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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.special.domain.entities.place.CommentPlace
import com.special.place.resource.R
import com.special.place.ui.my.postlist.TagList
import com.special.place.ui.my.setting.nickname.modify.addFocusCleaner
import com.special.place.ui.theme.*

@Composable
fun MyCommentScreen(
    postList: List<CommentPlace>
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommentPostList(postList)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CommentPostList(pList: List<CommentPlace>) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = scrollState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(pList.size) {
            CommentItem(pList, 0)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CommentItem(list: List<CommentPlace>, index: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 23.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.photo),
                contentDescription = "item",
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.width(176.dp)
            ) {
                Text(
                    text = list[index].placeType.name,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.grey_600),
                    style = Body1
                )
                Text(
                    text = list[index].name,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.grey_900),
                    style = Subtitle4
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            if (false) { //TODO bookmark is false
                // 보라
                Image(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp),
                    painter = painterResource(id = R.drawable.ic_bookmark_grey),
                    contentDescription = "bookmark",
                    colorFilter = ColorFilter.tint(Purple500)
                )
            } else {
                // 회색
                Image(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp),
                    painter = painterResource(id = R.drawable.ic_bookmark_grey),
                    contentDescription = "bookmark"
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_dots),
                contentDescription = "dots"
            )
        }
        Row() {
            Spacer(modifier = Modifier.width(64.dp))
            TagList(list[index].hashTags)
        }

    }

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
                text = list[index].comment.user.nickName + "님", fontSize = 14.sp, color = Grey900,
                style = Subtitle2
            )
            Row(
                modifier = Modifier.padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = list[index].comment.createdAt, color = Grey600, style = Caption
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
            text = list[index].comment.comment,
            fontSize = 18.sp,
            color = Grey900,
            style = BodyLong2
        )
    }

}
