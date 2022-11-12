package com.special.place.ui.main

import android.location.Location
import androidx.lifecycle.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.special.domain.entities.place.Place
import com.special.domain.repositories.PlaceRepository
import com.special.place.ui.place.map.PlaceEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(private val placeRepo: PlaceRepository) : ViewModel(), PlaceEventListener {
    private val _cameraPosition: MutableLiveData<CameraPosition> = MutableLiveData()
    private val _currentLocation: MutableLiveData<Location> = MutableLiveData()

    override val places: LiveData<List<Place>> = placeRepo.places.asLiveData()

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

        TODO("Not yet implemented")
    }

    override fun clickVisitPlace(placeId: String) {
        TODO("Not yet implemented")
    }

    init {
        viewModelScope.launch {


        }

    }

}

fun Location.toLatLnt(): LatLng = LatLng(latitude, longitude)