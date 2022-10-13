package com.special.place.proto.ui.place.register.location

import androidx.lifecycle.LiveData
import com.special.domain.entities.Coordinate

interface PlaceLocationEventListener {
    val displayLocation: LiveData<String>
    fun updateCameraPosition(coordinate: Coordinate)
}