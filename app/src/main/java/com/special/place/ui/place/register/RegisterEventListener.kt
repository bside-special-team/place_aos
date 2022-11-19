package com.special.place.ui.place.register

import androidx.lifecycle.LiveData

interface RegisterEventListener {
    val step: LiveData<PlaceRegisterStep>

    fun back()
    fun next()
}