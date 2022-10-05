package com.special.place.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.special.domain.entities.Place
import com.special.place.toMarker
import com.special.place.ui.place.register.PlaceRegisterActivity
import com.special.place.ui.theme.PlaceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val vm: PlacesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val chargerList by vm.places.observeAsState(listOf())
                    MainScaffold(chargerList) {
                        startActivity(PlaceRegisterActivity.newIntent(this))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MainScaffold(list: List<Place>, registerPlace: () -> Unit) {
    val cameraPosition = rememberCameraPositionState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "일상의 발견") }, actions = {
                IconButton(onClick = registerPlace) {
                    Icon(Icons.Outlined.Add, contentDescription = "add")
                }
            })
        }
    ) {
        NaverMap(
            cameraPositionState = cameraPosition
        ) {
            list.forEach {
                it.toMarker()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlaceTheme {
        MainScaffold(listOf()) {}
    }
}