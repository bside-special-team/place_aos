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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.special.place.resource.R
import com.special.place.ui.my.setting.addFocusCleaner

@Composable
fun PostScreen(postList: ArrayList<MyPostData>) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PostList(postList)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun PostList(list: ArrayList<MyPostData>) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = scrollState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(list.size) {
            PostItem(list, it)
        }
    }
}

@Composable
fun PostItem(list: ArrayList<MyPostData>, index: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_marker_question),
                contentDescription = "Andy Rubin",
                modifier = Modifier.width(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .width(176.dp)
            ) {
                Text(
                    text = list[index].placeType,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.grey_600)
                )
                Text(
                    text = list[index].placeName,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.grey_900)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            if (list[index].bookmark) {
                // 보라
                Image(
                    painter = painterResource(id = R.drawable.ic_bookmark_purple),
                    contentDescription = "bookmark"
                )
            } else {
                // 회색
                Image(
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

        TagList(list[index].hashTag)
    }
}

@Composable
fun TagList(list: List<String>) {
    Row() {
        for (i in 0 until list.size - 1) {
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .background(
                        color = colorResource(id = R.color.grey_100),
                        shape = RoundedCornerShape(12.dp)
                    ),
                text = list[i] + " "
            )
        }
    }
}