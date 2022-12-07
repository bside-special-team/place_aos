package com.special.place.ui.splash

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.special.place.resource.R
import com.special.place.ui.theme.*

class OnBoardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            PlaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    OnBoardingScreen(0)
                }

            }
        }
    }
}

@Composable
fun OnBoardingScreen(idx: Int) {
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 34.dp, horizontal = 24.dp),
            Arrangement.SpaceBetween
        ) {
            Text(text = "건너뛰기", style = Body1, color = Grey400)

            DotsIndicator(
                totalDots = 4,
                selectedIndex = idx,
                selectedColor = Purple500,
                unSelectedColor = Grey300
            )
            Text(text = "다음", style = Body1, color = Purple500)
        }
        Spacer(modifier = Modifier.height(43.dp))
        Column(
            modifier = Modifier.fillMaxHeight(),
            Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "우리동네 히든플레이스를\n" +
                            "공유해보세요!", style = Title1, textAlign = TextAlign.Center,
                    color = Grey900
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "일상의 인상 깊은 모든 순간들을 기록해보세요", style = Caption, color = Grey600)
            }

            Card(
                modifier = Modifier
                    .width(230.dp)
                    .padding(bottom = 20.dp),
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
                                when (idx) {
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
                    modifier = Modifier.height(410.dp)
                )
            }
        }


    }
}


@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color,
) {
    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()

    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor.copy(alpha = 0.4f))
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 3.dp))
            }
        }
    }
}