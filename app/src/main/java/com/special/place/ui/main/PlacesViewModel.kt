package com.special.place.ui.main

import android.location.Location
import androidx.lifecycle.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.LocationTrackingMode
import com.special.domain.entities.place.Place
import com.special.domain.repositories.PlaceRepository
import com.special.place.ui.place.map.PlaceEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(private val placeRepo: PlaceRepository) : ViewModel(), PlaceEventListener {
    private val _cameraPosition: MutableLiveData<CameraPosition> = MutableLiveData()
    private val _currentLocation: MutableLiveData<Location> = MutableLiveData()

    override val places: LiveData<List<Place>> = placeRepo.places.asLiveData()

    private val _trackingMode: MutableLiveData<LocationTrackingMode> = MutableLiveData()
    override val trackingMode: LiveData<LocationTrackingMode> = _trackingMode

    override val visibleCurrentLocationButton: LiveData<Boolean> = Transformations.map(_cameraPosition) {
//        val currentLocation = _currentLocation.value?.toLatLnt()
//        currentLocation != null && it.target != currentLocation
        false
    }

    override fun updateCameraPosition(camera: CameraPosition) {
        _cameraPosition.postValue(camera)
    }

    override fun updateCurrentLocation(location: Location) {
        _currentLocation.postValue(location)
    }

    override val hiddenPlaceCount: LiveData<Int> = liveData { emit(17) }
    override val landmarkCount: LiveData<Int> = liveData { emit(5) }

    override fun clickTourStart() {

    }

    override fun clickVisitPlace(placeId: String) {

    }

    override fun updateTrackingMode(mode: LocationTrackingMode) {
        if (_trackingMode.value != mode) {
            _trackingMode.postValue(mode)
        }
    }

}

fun Location.toLatLnt(): LatLng = LatLng(latitude, longitude)