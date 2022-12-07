package com.special.place.ui.my.postlist

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (val state = places.loadState.prepend) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }
            is LoadState.Error -> {
                Empty(postType)
            }
        }
        when (val state = places.loadState.refresh) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }
            is LoadState.Error -> {
                Empty(postType)
            }
        }

        if (places.itemCount == 0) {
            Empty(postType)
        } else {
            items(places) { place ->
                if (place != null) {
                    PostItem(place)
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

        when (val state = places.loadState.append) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }
            is LoadState.Error -> {
                Empty(postType)
            }
        }
    }
}


private fun LazyListScope.Loading() {
    item {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
}

private fun LazyListScope.Empty(postType: PostType) {
    item {
        if (postType == PostType.MyPlaces) {
            EmptyScreen("작성한 게시물이 없어요 \uD83E\uDD72")
        } else {
            EmptyScreen("추천한 게시물이 없어요 \uD83E\uDD72")
        }
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
