package com.special.place.ui.place.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import com.naver.maps.geometry.LatLng
import com.special.domain.entities.Coordinate
import com.special.place.ui.place.register.besttime.VisitTimePicker
import com.special.place.ui.place.register.category.SelectCategory
import com.special.place.ui.place.register.input.InputPlaceDescription
import com.special.place.ui.place.register.input.InputPlaceName
import com.special.place.ui.place.register.location.DisplayLocation
import com.special.place.ui.place.register.location.NaverMapView
import com.special.place.ui.theme.PlaceTheme
import dagger.hilt.android.AndroidEntryPoint


const val LATITUDE = "lat"
const val LONGITUDE = "lng"

@AndroidEntryPoint
class PlaceRegisterActivity : ComponentActivity() {
    companion object {
        fun newIntent(context: Context, latLng: LatLng? = null): Intent = Intent(context, PlaceRegisterActivity::class.java).apply {
            if (latLng != null) {
                putExtra(LATITUDE, latLng.latitude)
                putExtra(LONGITUDE, latLng.longitude)
            }
        }
    }

    private val vm: PlaceRegisterViewModel by viewModels()
    private val coordinate: LatLng? by lazy {
        val lat = intent.getDoubleExtra(LATITUDE, -1.0)
        val lng = intent.getDoubleExtra(LONGITUDE, -1.0)

        if (lat > 0 && lng > 0) {
            LatLng(lat, lng)
        } else {
            null
        }
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
                    RegisterScreen(vm, coordinate)
                }
            }
        }

        initViewModel()
    }

    private fun initViewModel() {
        vm.placeRegisterResult.observe(this) {
            if (it.isSuccess) {
                Toast.makeText(applicationContext, it.getOrNull(), Toast.LENGTH_SHORT).show()

                finish()
            } else {

                Toast.makeText(applicationContext, "등록에 실패 하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun RegisterScreen(vm: PlaceRegisterViewModel, initialCoordinate: LatLng? = null) {
    Scaffold(topBar = { TopAppBar(title = { Text("플레이스 등록") }) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            NaverMapView(vm, initialCoordinate)

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