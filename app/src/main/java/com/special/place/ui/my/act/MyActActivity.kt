package com.special.place.ui.my.act

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.special.place.ui.theme.PlaceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.special.place.resource.R
// 임의 데이터
data class MyPostData(
    val placeType: String,
    val placeName: String,
    val hashTag: List<String>,
    val bookmark: Boolean
)
val postList = ArrayList<MyPostData>()
val postList2 = ArrayList<MyPostData>()


@AndroidEntryPoint
class MyActActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postList.add(MyPostData("히든플레이스", "가로수길 아래", listOf("조용한", "힙한", "신기한"), true))
        postList.add(MyPostData("히든플레이스", "가로수길 아래", listOf("시끄러운", "힙한", "신기한"), false))
        postList.add(MyPostData("히든플레이스", "가로수길 아래", listOf("얍", "힙한", "신기한"), false))

        postList2.add(MyPostData("히든플레이스", "추천 아래", listOf("조용한", "힙한", "신기한"), true))
        postList2.add(MyPostData("히든플레이스", "가로수길 아래", listOf("시끄러운", "힙한", "신기한"), false))
        postList2.add(MyPostData("히든플레이스", "가로수길 아래", listOf("얍", "힙한", "신기한"), false))

        val pages = listOf(
            resources.getString(R.string.tab_my_post), resources.getString(R.string.tab_my_comment),
            resources.getString(R.string.tab_recommend), resources.getString(R.string.tab_bookmark)
        )
        setContent {
            PlaceTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        val pagerState = rememberPagerState()
                        val coroutineScope = rememberCoroutineScope()

                        TabRow(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = Color.White,
                            contentColor = Color.Black,
                            selectedTabIndex = pagerState.currentPage,
                            indicator = { tabPositions ->
                                TabRowDefaults.Indicator(
                                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                                )
                            }
                        ) {
                            pages.forEachIndexed { index, title ->
                                Tab(
                                    text = { Text(text = title) },
                                    selected = pagerState.currentPage == index,
                                    onClick = {
                                        coroutineScope.launch {
                                            pagerState.scrollToPage(index)
                                        }
                                    }
                                )
                            }
                        }

                        HorizontalPager(
                            count = pages.size,
                            state = pagerState,
                        ) { page ->
                            when(page){
                                0->PostScreen(postList) // 내 게시물
                                1->MyCommentScreen() // 내 댓글
                                2->PostScreen(postList2) // 추천
                                3->PostScreen(postList) // 북마크
                            }
                        }
                    }
                }
            }
        }
    }
}