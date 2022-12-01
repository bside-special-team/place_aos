package com.special.place.ui.my.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.special.place.resource.R
import com.special.place.ui.my.MyInformationViewModel
import com.special.place.ui.my.postlist.PostScreen
import com.special.place.ui.theme.Grey500
import com.special.place.ui.theme.PlaceTheme
import com.special.place.ui.theme.Subtitle2
import com.special.place.ui.theme.Subtitle4
import com.special.place.ui.utils.MyTopAppBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// 임의 데이터
data class MyPostData(
    val placeType: String,
    val placeName: String,
    val hashTag: List<String>,
    val bookmark: Boolean
)

@AndroidEntryPoint
class MyActActivity : ComponentActivity() {

    private val vm: MyInformationViewModel by viewModels()

    companion object {
        fun newIntent(context: Context) = Intent(context, MyActActivity::class.java)
    }

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBack: () -> Unit = {
            finish()
        }

        val pages = listOf(
            resources.getString(R.string.tab_my_post), resources.getString(R.string.tab_my_comment),
            resources.getString(R.string.tab_recommend)
        )
        setContent {
            PlaceTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = Color.White
                ) {
                    Scaffold(
                        topBar = {
                            MyTopAppBar(
                                title = stringResource(id = R.string.tab_my_act),
                                navigationType = "back",
                                navigationListener = { onBack() },
                                actionType = "",
                                actionListener = {}
                            )
                        },
                        content = {

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.Start
                            ) {
                                val pagerState = rememberPagerState()
                                val coroutineScope = rememberCoroutineScope()
                                val density = LocalDensity.current
                                var textSize = remember { 12.dp }
                                val tabWidths = remember {
                                    val tabWidthStateList = mutableStateListOf<Dp>()
                                    repeat(pages.size) {
                                        tabWidthStateList.add(0.dp)
                                    }
                                    tabWidthStateList
                                }
                                ScrollableTabRow(
                                    backgroundColor = Color.White,
                                    contentColor = Color.Black,
                                    edgePadding = 0.dp,
                                    divider = {},
                                    selectedTabIndex = pagerState.currentPage,
                                    indicator = { tabPositions ->
                                        TabRowDefaults.Indicator(
                                            modifier = Modifier.customTabIndicatorOffset(
                                                currentTabPosition = tabPositions[pagerState.currentPage],
                                                tabWidth = tabWidths[pagerState.currentPage]
                                            )
                                        )
                                    },
                                    modifier = Modifier.padding(start = 12.dp)
                                ) {
                                    pages.forEachIndexed { index, title ->
                                        Tab(
                                            text = {
                                                Text(
                                                    modifier = Modifier
                                                        .wrapContentWidth(),
                                                    text = title, style = Subtitle4,
                                                    onTextLayout = {
//                                                    textSize = textLayoutResult.size.width.dp
//                                                    tabWidths[index] =
//                                                        with(density) { textLayoutResult.size.width.toDp() }
                                                    })
                                            },
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
                                    when (page) {
                                        0 -> if (vm.myPlace.value!!.isEmpty()) {
                                            EmptyScreen("작성한 게시물이 없어요 \uD83E\uDD72")
                                        } else {
                                            PostScreen(
                                                vm.myPlace.value!!
                                            )
                                        }
                                        1 -> if (vm.myCommentPlace.value!!.isEmpty()) {
                                            EmptyScreen("작성한 댓글이 없어요 \uD83E\uDD72")
                                        } else {
                                            MyCommentScreen(
                                                vm.myCommentPlace.value!!
                                            )
                                        }
                                        2 -> if (vm.myRecommendPlace.value!!.isEmpty()) {
                                            EmptyScreen("추천한 게시물이 없어요 \uD83E\uDD72")
                                        } else {
                                            PostScreen(
                                                vm.myRecommendPlace.value!!
                                            )
                                        }

//                                        3 -> PostScreen(
//                                            vm.myBookmarkPlace.value!!
//                                        ) // 북마크
                                    }
                                }
                            }
                        }

                    )

                }
            }
        }
    }
}

/* 탭 글씨 크기에 맞추기 --> textLayout만 적용됨 */
fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    tabWidth: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "customTabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}

@Composable
fun EmptyScreen(text: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(72.dp))
        Image(
            painter = painterResource(R.drawable.ic_empty_comment),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = text,
            style = Subtitle2,
            color = Grey500
        )
        Spacer(modifier = Modifier.height(200.dp))
    }
}