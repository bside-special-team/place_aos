package com.special.place.ui.place.register.input

import androidx.lifecycle.LiveData
import com.special.place.ui.UiState
import com.special.place.ui.place.register.PlaceRegisterStep
import com.special.place.ui.place.register.RegisterEventListener

interface PlaceInputEventListener : RegisterEventListener {
    val placeName: LiveData<String>
    fun setPlaceName(name: String)

    companion object {
        fun empty() = object : PlaceInputEventListener {
            override val placeName: LiveData<String>
                get() = TODO("Not yet implemented")

            override fun setPlaceName(name: String) {
                TODO("Not yet implemented")
            }

            override val step: LiveData<PlaceRegisterStep>
                get() = TODO("Not yet implemented")
            override val uiState: LiveData<UiState>
                get() = TODO("Not yet implemented")

            override fun back() {
                TODO("Not yet implemented")
            }

            override fun next() {
                TODO("Not yet implemented")
            }

        }
    }
}