package com.special.place.ui.place.map

import android.location.Location
import androidx.lifecycle.LiveData
import com.naver.maps.map.CameraPosition
import com.special.domain.entities.place.Place

interface PlaceEventListener {
    val places: LiveData<List<Place>>
    val visibleCurrentLocationButton: LiveData<Boolean>

    fun updateCameraPosition(camera: CameraPosition)
    fun updateCurrentLocation(location: Location)

    val hiddenPlaceCount: LiveData<Int>
    val landmarkCount: LiveData<Int>

    fun clickTourStart()

    fun clickVisitPlace(placeId: String)
}