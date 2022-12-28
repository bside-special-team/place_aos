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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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

    private val _visitMode: MutableLiveData<String> = MutableLiveData()
    override val visitMode: LiveData<String> = _visitMode


    override val visibleCurrentLocationButton: LiveData<Boolean> = _trackingMode.map {
        it != LocationTrackingMode.Follow
    }

    private val _tourTime: MutableLiveData<String> = MutableLiveData()
    override val tourTime: LiveData<String> = _tourTime

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

    override val hiddenPlaceCount: LiveData<Int> =
        placeRepo.placeCount.map { it.hiddenPlaceCount }.asLiveData()
    override val landmarkCount: LiveData<Int> =
        placeRepo.placeCount.map { it.landmarkCount }.asLiveData()


    override fun clickTourStart(placeId: String) {
        _visitMode.value = placeId
        timerStart()
    }

    override fun clickTourEnd() {
        _visitMode.postValue("")
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

    override val distanceText: LiveData<String> = _currentPlace.map {
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

    override val distance: LiveData<Int> = _currentPlace.map { currentPlace ->
        _currentLocation.value?.toLatLnt()?.let {
            currentPlace.coordinate.toLatLng().distanceTo(it).toInt()
        } ?: Int.MAX_VALUE
    }

    // 현재 방문하고 있는 플레이스
    private val _currentVisitPlace: MutableLiveData<Place> = MutableLiveData()
    override val currentVisitPlace: LiveData<Place> = _currentVisitPlace

    override fun updateCurrentVisitPlace(place: Place) {
        placeRepo.selectVisitPlace(place)
        _currentVisitPlace.postValue(place)
    }

    // 방문인증 - 남은 거리
    override val currentDistance: LiveData<Int> = _currentLocation.map {
        _currentVisitPlace.value?.coordinate?.toLatLng()?.distanceTo(it.toLatLnt())?.toInt()
            ?: Int.MAX_VALUE
    }
    override val currentDistanceText: LiveData<String> = _currentLocation.map {
        val placeLatLng = _currentVisitPlace.value?.coordinate?.toLatLng()
        if (placeLatLng == null) {
            "-"
        } else {
            val distance = placeLatLng.distanceTo(it.toLatLnt()).toInt()

            if (distance > 1000) {
                "${distance / 1000} km"
            } else {
                "$distance m"
            }
        }
    }

    /* 타이머 */
    private lateinit var job: Job
    private var interval: Long = 1000

    private fun timerStart() {
        if (::job.isInitialized) job.cancel()
        job = viewModelScope.launch {
            val time: Long = System.currentTimeMillis()
            while (_visitMode.value!!.isNotEmpty()) {
                _tourTime.value =
                    (System.currentTimeMillis() - time).displayTime()
                delay(interval)
            }
        }
    }

    private fun timerStop() {
        if (::job.isInitialized) job.cancel()
    }

    fun Long.displayTime(): String {
        if (this <= 0L) {
            return "00:00"
        }

        val m = this / 1000 % 3600 / 60
        val s = this / 1000 % 60

        return "$m:${displaySlot(s)}"
    }

    private fun displaySlot(count: Long): String {
        return if (count / 10L > 0) {
            "$count"
        } else {
            "0$count"
        }
    }
}

fun Location.toLatLnt(): LatLng = LatLng(latitude, longitude)
fun LatLngBounds.toCoordinate(): CoordinateBounds = CoordinateBounds(
    from = Coordinate(latitude = southEast.latitude.toString(), longitude = southEast.longitude.toString()),
    to = Coordinate(latitude = northEast.latitude.toString(), longitude = northEast.longitude.toString()),
)

