package com.special.place.ui.my.postlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.special.domain.entities.place.Place
import com.special.place.ui.my.MyInformationViewModel
import com.special.place.ui.my.act.EmptyScreen
import com.special.place.ui.my.act.PostType
import com.special.place.ui.my.setting.nickname.modify.addFocusCleaner

@Composable
fun PostScreen(
    vm: MyInformationViewModel,
    postType: PostType
) {
    val places = if (postType == PostType.MyPlaces)
        vm.myPlace.collectAsLazyPagingItems()
    else
        vm.myRecommendPlace.collectAsLazyPagingItems()

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (places.itemCount < 1) {
            if (postType == PostType.MyPlaces) {
                EmptyScreen("작성한 게시물이 없어요 \uD83E\uDD72")
            } else {
                EmptyScreen("추천한 게시물이 없어요 \uD83E\uDD72")
            }
        } else {
            PostList(places)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun PostList(list: LazyPagingItems<Place>) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = scrollState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(list) { place ->
            if (place != null) {
                PostItem(place)
            }
        }
    }
}
