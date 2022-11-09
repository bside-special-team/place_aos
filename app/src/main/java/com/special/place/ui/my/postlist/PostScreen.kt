package com.special.place.ui.my.postlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.special.place.ui.my.act.MyPostData
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