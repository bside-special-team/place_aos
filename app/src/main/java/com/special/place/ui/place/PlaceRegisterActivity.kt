package com.special.place.ui.place

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.special.domain.entities.PlaceCategory
import com.special.place.R
import com.special.place.showTimePicker
import com.special.place.ui.theme.PlaceTheme
import dagger.hilt.android.AndroidEntryPoint

val categories = listOf(
    PlaceCategory("1234", "001", "집"),
    PlaceCategory("3221", "002", "회사"),
    PlaceCategory("1111", "003", "밥집"),
)

@AndroidEntryPoint
class PlaceRegisterActivity : ComponentActivity() {
    companion object {
        fun newIntent(context: Context): Intent = Intent(context, PlaceRegisterActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PlaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RegisterScreen()
                }
            }
        }
    }

}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun RegisterScreen() {
    val cameraState = rememberCameraPositionState()
    var visitTime by remember { mutableStateOf("") }

    val timeEndPicker = showTimePicker(LocalContext.current) { hour, minute ->
        visitTime += String.format(" - %02d:%02d", hour, minute)
    }

    val timeStartPicker = showTimePicker(LocalContext.current) { hour, minute ->
        visitTime = String.format("%02d:%02d", hour, minute)
        timeEndPicker.show()
    }


    Scaffold(topBar = { TopAppBar(title = { Text("플레이스 등록") }) }) {

        Column(modifier = Modifier.fillMaxWidth().verticalScroll(ScrollState(0))) {
            Box(contentAlignment = Alignment.Center) {
                NaverMap(
                    cameraPositionState = cameraState,

                    modifier = Modifier
                        .height((LocalConfiguration.current.screenWidthDp*0.6).dp)
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Icon(painterResource(id = R.drawable.ic_target_location), contentDescription = "target")
            }

            if (cameraState.isMoving.not()) {
                Log.d("CameraState", cameraState.position.target.toString())
                // TODO: viewModel update position
                // TODO: viewModel reverseGeocoding
            }

            Text(cameraState.position.target.toString())

            CategoryDropdown(list = categories)

            OutlinedTextField(value = "", onValueChange = {}, label = { Text("플레이스 이름") }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp))

            OutlinedTextField(value = "", onValueChange = {}, label = { Text("플레이스 설명") }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp))

            Text(text = "방문하기 좋은 시간")
            Text(text = visitTime, modifier = Modifier.clickable {
                timeStartPicker.show()
            })
        }
    }
}

@Composable
fun CategoryDropdown(list: List<PlaceCategory>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(text = list[selectedIndex].name, modifier = Modifier
            .clickable { expanded = true }
            .padding(12.dp)
            .fillMaxWidth())
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.fillMaxWidth()) {
            list.forEachIndexed { index, item ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                }) {
                    Text(item.name)
                }

            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlaceTheme {
        CategoryDropdown(categories)
    }
}