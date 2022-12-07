package com.special.place.ui.splash

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                    val imageLoader = ImageLoader.Builder(this)
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
                        Spacer(modifier = Modifier.height(60.dp))
                        Text(
                            text = "우리동네 히든플레이스를\n" +
                                    "공유해보세요!", style = Title1, textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "일상의 인상 깊은 모든 순간들을 기록해보세요", style = Caption)
                        Spacer(modifier = Modifier.height(71.dp))
                        Card(
                            modifier = Modifier
                                .wrapContentSize(),
                            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                            border = BorderStroke(width = 4.dp, color = Grey400),
                            elevation = 0.dp,
                            backgroundColor = Purple500,

                            ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest
                                        .Builder(context = this@OnBoardingActivity)
                                        .data(data = R.drawable.on_boarding_3)
                                        .build(),
                                    imageLoader = imageLoader,
                                ),
                                contentDescription = null,
                            )
                        }


                    }

                }

            }
        }
    }
}