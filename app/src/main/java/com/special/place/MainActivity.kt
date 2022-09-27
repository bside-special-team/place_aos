package com.special.place

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naver.maps.map.compose.*
import com.special.mock.model.EvCharger
import com.special.place.ui.theme.PlaceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val vm: ChargerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val chargerList by vm.chargerList.observeAsState(listOf())
                    Greeting(chargerList)
                }
            }
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun Greeting(list: List<EvCharger>) {
    NaverMap {
        list.forEach {
            Marker(
                state = MarkerState(it.toLatLnt()),
                captionText = it.csNm,
                captionRequestedWidth = 200.dp,
                captionAligns = arrayOf(Align.Top),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlaceTheme {
        Greeting(listOf())
    }
}