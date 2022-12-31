package com.special.place.ui.place.map

import android.location.Location
import androidx.lifecycle.LiveData
import coil.request.ImageRequest
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.LocationTrackingMode
import com.special.domain.entities.place.Place

interface PlaceEventListener {
    val places: LiveData<List<Place>>
    val currentPlace: LiveData<Place>
    val currentVisitPlace: LiveData<Place>

    val trackingMode: LiveData<LocationTrackingMode>
    val visitMode: LiveData<String>

    val visibleCurrentLocationButton: LiveData<Boolean>

    fun updateCameraPosition(camera: CameraPositionState)
    fun updateCurrentLocation(location: Location)
    fun updateCurrentPlace(place: Place)
    fun updateCurrentVisitPlace(place: Place)

    val hiddenPlaceCount: LiveData<Int>
    val landmarkCount: LiveData<Int>

    val distanceText: LiveData<String>
    val distance: LiveData<Int>

    val currentDistanceText: LiveData<String>
    val currentDistance: LiveData<Int>

    val tourTime: LiveData<String>

    fun clickTourStart(placeId: String)
    fun clickTourEnd(placeId: String)

    fun clickVisitPlace(placeId: String)

    fun updateTrackingMode(mode: LocationTrackingMode)

    fun coilRequest(uuid: String): ImageRequest
}