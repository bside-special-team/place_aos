package com.special.place.ui.place.register.input

import androidx.lifecycle.LiveData

interface PlaceInputEventListener {
    val placeName: LiveData<String>
    fun setPlaceName(name: String)
}