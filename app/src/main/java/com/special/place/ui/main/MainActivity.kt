package com.special.place.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.special.domain.entities.Place
import com.special.place.toMarker
import com.special.place.ui.place.register.PlaceRegisterActivity
import com.special.place.ui.theme.PlaceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScaffold(list: List<Place>, registerPlace: () -> Unit) {
    val cameraPosition = rememberCameraPositionState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    var selectedPlace by remember { mutableStateOf<Place?>(null) }

    if (list.isNotEmpty()) {
        selectedPlace = list.first()
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        topBar = {
            TopAppBar(title = { Text(text = "일상의 발견") }, actions = {
                IconButton(onClick = registerPlace) {
                    Icon(Icons.Outlined.Add, contentDescription = "add")
                }
            })
        },
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(200.dp)
            ) {
                Row(modifier = Modifier.padding(bottom = 16.dp)) {
                    Text(selectedPlace?.category?.name ?: "", style = TextStyle())
                    Text(
                        text = selectedPlace?.name ?: "TITLE",
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Text(
                    text = selectedPlace?.description ?: "DESCRIPTION",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(text = "${selectedPlace?.bestStartTime}에서 ${selectedPlace?.bestEndTime}사이에 방문 하기 좋아요.")

            }
        },
        sheetPeekHeight = 40.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetElevation = 5.dp
    ) {
        NaverMap(
            cameraPositionState = cameraPosition,
            onMapClick = { _, _ ->
                coroutineScope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
        ) {
            list.forEach {
                it.toMarker {
                    selectedPlace = it

                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }

                }
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