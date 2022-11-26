package com.special.place.ui.place.register

import androidx.lifecycle.LiveData
import com.special.place.ui.UiState

interface RegisterEventListener {
    val step: LiveData<PlaceRegisterStep>
    val uiState: LiveData<UiState>

    fun back()
    fun next()
}