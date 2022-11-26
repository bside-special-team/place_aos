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

    val trackingMode: LiveData<LocationTrackingMode>

    val visibleCurrentLocationButton: LiveData<Boolean>

    fun updateCameraPosition(camera: CameraPositionState)
    fun updateCurrentLocation(location: Location)
    fun updateCurrentPlace(place: Place)

    val hiddenPlaceCount: LiveData<Int>
    val landmarkCount: LiveData<Int>

    fun clickTourStart()

    fun clickVisitPlace(placeId: String)

    fun updateTrackingMode(mode: LocationTrackingMode)

    fun coilRequest(uuid: String): ImageRequest
}