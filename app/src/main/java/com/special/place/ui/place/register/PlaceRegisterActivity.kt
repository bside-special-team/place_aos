package com.special.place.ui.place.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.special.place.ui.place.register.besttime.VisitTimePicker
import com.special.place.ui.place.register.category.SelectCategory
import com.special.place.ui.place.register.input.InputPlaceDescription
import com.special.place.ui.place.register.input.InputPlaceName
import com.special.place.ui.place.register.location.DisplayLocation
import com.special.place.ui.place.register.location.NaverMapView
import com.special.place.ui.theme.PlaceTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlaceRegisterActivity : ComponentActivity() {
    companion object {
        fun newIntent(context: Context): Intent = Intent(context, PlaceRegisterActivity::class.java)
    }

    private val vm: PlaceRegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PlaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RegisterScreen(vm)
                }
            }
        }

        initViewModel()
    }

    private fun initViewModel() {
        vm.placeRegisterResult.observe(this) {
            //TODO: Error Handling
        }
    }
}

@Composable
fun RegisterScreen(vm: PlaceRegisterViewModel) {
    Scaffold(topBar = { TopAppBar(title = { Text("플레이스 등록") }) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            NaverMapView(vm)

            DisplayLocation(vm)

            InputPlaceName(vm)

            InputPlaceDescription(vm)

            SelectCategory(vm)

            VisitTimePicker(vm)

            Button(
                onClick = { vm.registerPlace() }, modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text("등록하기")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlaceTheme {

    }
}