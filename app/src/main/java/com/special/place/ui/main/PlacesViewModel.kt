package com.special.place.ui.main

import android.location.Location
import android.util.Log
import androidx.lifecycle.*
import coil.request.ImageRequest
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.LocationTrackingMode
import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.CoordinateBounds
import com.special.domain.entities.place.Place
import com.special.domain.repositories.PlaceRepository
import com.special.place.toLatLng
import com.special.place.ui.place.map.PlaceEventListener
import com.special.place.util.CoilRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placeRepo: PlaceRepository,
    private val coilRequest: CoilRequest
) : ViewModel(), PlaceEventListener {
    private val _cameraBounds: MutableLiveData<CoordinateBounds> = MutableLiveData()
    private val _currentLocation: MutableLiveData<Location> = MutableLiveData()

    override val places: LiveData<List<Place>> = placeRepo.places.asLiveData()

    private val _trackingMode: MutableLiveData<LocationTrackingMode> = MutableLiveData()
    override val trackingMode: LiveData<LocationTrackingMode> = _trackingMode

    override val visibleCurrentLocationButton: LiveData<Boolean> =
        Transformations.map(_cameraBounds) {
            _trackingMode.value != LocationTrackingMode.Follow
        }

    override fun updateCameraPosition(camera: CameraPositionState) {
        camera.contentBounds?.toCoordinate()?.let {
            Log.d("Camera Bounds", "$it")
            _cameraBounds.postValue(it)
            placeRepo.updateCoordinate(it)
        }
    }

    override fun updateCurrentLocation(location: Location) {
        _currentLocation.postValue(location)
    }

    override val hiddenPlaceCount: LiveData<Int> = placeRepo.placeCount.map { it.hiddenPlaceCount }.asLiveData()
    override val landmarkCount: LiveData<Int> = placeRepo.placeCount.map { it.landmarkCount }.asLiveData()


    override fun clickTourStart() {

    }

    override fun clickVisitPlace(placeId: String) {
        viewModelScope.launch {
            placeRepo.visitPlace(placeId)
        }
    }

    override fun updateTrackingMode(mode: LocationTrackingMode) {
        if (_trackingMode.value != mode) {
            _trackingMode.postValue(mode)
        }
    }

    private val _currentPlace: MutableLiveData<Place> = MutableLiveData()
    override val currentPlace: LiveData<Place> = _currentPlace

    override fun updateCurrentPlace(place: Place) {
        placeRepo.selectPlace(place)
        _currentPlace.postValue(place)
    }

    override fun coilRequest(uuid: String): ImageRequest {
        return coilRequest.myImageRequest(uuid)
    }

    override val distance: LiveData<String> = _currentPlace.map {
        val currentLatLnt = _currentLocation.value?.toLatLnt()

        if (currentLatLnt == null) {
            "-"
        } else {
            val distance = it.coordinate.toLatLng().distanceTo(currentLatLnt).toInt()

            if (distance > 1000) {
                "${distance / 1000} km"
            } else {
                "$distance m"
            }
        }
    }
}

fun Location.toLatLnt(): LatLng = LatLng(latitude, longitude)
fun LatLngBounds.toCoordinate(): CoordinateBounds = CoordinateBounds(
    from = Coordinate(latitude = southEast.latitude.toString(), longitude = southEast.longitude.toString()),
    to = Coordinate(latitude = northEast.latitude.toString(), longitude = northEast.longitude.toString()),
)