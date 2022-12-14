package com.special.place.ui.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.special.place.resource.R
import com.special.place.ui.base.BaseActivity
import com.special.place.ui.place.information.DotsIndicator
import com.special.place.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity() {
    companion object {
        fun newIntent(context: Context) = Intent(context, OnBoardingActivity::class.java)
    }

    private val vm: OnBoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        vm.init()

        setContent {
            PlaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    OnBoardingScreen(vm)
                }

            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(vm: OnBoardingListener) {
    val ctx = LocalContext.current
    val imageLoader = ImageLoader.Builder(ctx)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    val onBoardingList: List<OnBoarding> by vm.onBoardingList.observeAsState(initial = listOf())

    val state = rememberPagerState()
    val scope = rememberCoroutineScope()
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 34.dp, horizontal = 24.dp),
            Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.clickable { (ctx as? Activity)?.finish() ?: Unit },
                text = "건너뛰기", style = Body1, color = Grey400
            )

            DotsIndicator(
                totalDots = 4,
                selectedIndex = state.currentPage,
                selectedColor = Purple500,
                unSelectedColor = Grey300
            )
            if (state.currentPage == 3) {
                Text(
                    modifier = Modifier.clickable { (ctx as? Activity)?.finish() ?: Unit },
                    text = "시작하기", style = Body1, color = Purple500
                )
            } else {
                Text(
                    modifier = Modifier.clickable {
                        scope.launch {
                            state.scrollToPage(state.currentPage + 1)
                        }
                    },
                    text = "다음", style = Body1, color = Purple500
                )
            }
        }
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth(),
            count = onBoardingList.size, state = state
        ) { page ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = onBoardingList[page].content,
                        style = Title1,
                        textAlign = TextAlign.Center,
                        color = Grey900
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = onBoardingList[page].subContent,
                        style = Caption,
                        color = Grey600,
                        textAlign = TextAlign.Center
                    )
                }

                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 75.dp, bottom = 40.dp),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    border = BorderStroke(width = 4.dp, color = Grey400),
                    elevation = 0.dp,
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(context = ctx)
                                .data(
                                    data =
                                    when (onBoardingList[page].idx) {
                                        0 -> {
                                            R.drawable.on_boarding_1
                                        }
                                        1 -> {
                                            R.drawable.on_boarding_2
                                        }
                                        2 -> {
                                            R.drawable.on_boarding_3
                                        }
                                        else -> {
                                            R.drawable.on_boarding_4
                                        }
                                    }
                                )
                                .build(),
                            imageLoader = imageLoader,
                        ),
                        contentDescription = null,
                        modifier = Modifier.wrapContentSize()
                    )
                }
            }
        }
    }

}
