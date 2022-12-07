package com.special.place.ui.my.act

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.special.domain.entities.place.CommentPlace
import com.special.place.resource.R
import com.special.place.ui.my.MyInformationViewModel
import com.special.place.ui.my.postlist.TagList
import com.special.place.ui.my.setting.nickname.modify.addFocusCleaner
import com.special.place.ui.theme.*

@Composable
fun MyCommentScreen(
    vm: MyInformationViewModel
) {
    val comments = vm.myCommentPlace.collectAsLazyPagingItems()
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = comments.loadState.prepend) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }
            is LoadState.Error -> {
                Empty()
            }
        }
        when (val state = comments.loadState.refresh) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }
            is LoadState.Error -> {
                Empty()
            }
        }
        items(comments) { item ->
            if (item != null) {
                CommentItem(item)
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        when (val state = comments.loadState.append) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }
            is LoadState.Error -> {
                Empty()
            }
        }

    }
}

private fun LazyListScope.Loading() {
    item {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
}

private fun LazyListScope.Empty() {
    item {
        EmptyScreen("작성한 댓글이 없어요 \uD83E\uDD72")
    }
}

@Composable
fun CommentItem(comment: CommentPlace) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                    text = comment.placeType.name,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.grey_600),
                    style = Body1
                )
                Text(
                    text = comment.name,
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
            TagList(comment.hashTags)
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
                text = comment.comment.user.nickName + "님", fontSize = 14.sp, color = Grey900,
                style = Subtitle2
            )
            Row(
                modifier = Modifier.padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = comment.comment.createdAt, color = Grey600, style = Caption
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
            text = comment.comment.comment,
            fontSize = 18.sp,
            color = Grey900,
            style = BodyLong2
        )
    }

}
