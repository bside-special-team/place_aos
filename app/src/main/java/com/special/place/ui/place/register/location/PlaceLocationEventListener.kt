package com.special.place.ui.place.register.location

import androidx.lifecycle.LiveData
import com.special.domain.entities.place.Coordinate
import com.special.place.ui.place.register.RegisterEventListener

interface PlaceLocationEventListener : RegisterEventListener {
    val displayLocation: LiveData<String>
    fun updateCameraPosition(coordinate: Coordinate)
}