package com.special.place.ui.place.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import com.naver.maps.geometry.LatLng
import com.special.domain.entities.place.Coordinate
import com.special.place.ui.place.register.complete.PlaceRegisterCompleteScreen
import com.special.place.ui.place.register.hashtags.HashtagStep
import com.special.place.ui.place.register.input.InputPlaceNameStep
import com.special.place.ui.place.register.location.LocationStep
import com.special.place.ui.place.register.select.picture.SelectPictureStep
import com.special.place.ui.theme.PlaceTheme
import com.special.place.util.ContentResolverHelper
import com.special.place.util.LocationFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


const val LATITUDE = "lat"
const val LONGITUDE = "lng"

@AndroidEntryPoint
class PlaceRegisterActivity : ComponentActivity() {
    companion object {
        fun newIntent(context: Context, latLng: LatLng? = null): Intent =
            Intent(context, PlaceRegisterActivity::class.java).apply {
                if (latLng != null) {
                    putExtra(LATITUDE, latLng.latitude)
                    putExtra(LONGITUDE, latLng.longitude)
                }
            }
    }

    private val lastLatLng : LatLng? by lazy {
        val latitude = intent.getDoubleExtra(LATITUDE, -1.0)
        val longitude = intent.getDoubleExtra(LONGITUDE, -1.0)
        if (latitude >= 0 && longitude >= 0) {
            LatLng(latitude, longitude)
        } else {
            null
        }
    }

    @Inject
    lateinit var contentResolverHelper: ContentResolverHelper

    @Inject
    lateinit var locationFactory: LocationFactory

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
                    RegisterScreen(vm, locationFactory, initialLocation = lastLatLng)
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
fun RegisterScreen(vm: PlaceRegisterViewModel, locationFactory: LocationFactory, initialLocation: LatLng? = null) {
    val step by vm.step.observeAsState(initial = PlaceRegisterStep.Location)

    when (step) {
        PlaceRegisterStep.Location -> LocationStep(vm, locationFactory, initialLocation)
        PlaceRegisterStep.SelectPicture -> SelectPictureStep(vm)
        PlaceRegisterStep.InputPlaceName -> InputPlaceNameStep(vm)
        PlaceRegisterStep.ChooseHashtag -> HashtagStep(vm)
        PlaceRegisterStep.Complete -> PlaceRegisterCompleteScreen()
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlaceTheme {

    }
}