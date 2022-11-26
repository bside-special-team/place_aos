package com.special.place.ui.place.register.select.picture

import android.net.Uri
import androidx.lifecycle.LiveData
import com.special.place.ui.UiState
import com.special.place.ui.place.register.PlaceRegisterStep
import com.special.place.ui.place.register.RegisterEventListener

interface SelectPictureEventListener : RegisterEventListener {
    val pictures: LiveData<List<Uri>>

    fun selectPicture(uri: Uri)
    fun unselectPicture(uri: Uri)

    companion object {
        fun empty(): SelectPictureEventListener = object : SelectPictureEventListener {
            override val pictures: LiveData<List<Uri>>
                get() = TODO("Not yet implemented")

            override fun selectPicture(uri: Uri) {
                TODO("Not yet implemented")
            }

            override fun unselectPicture(uri: Uri) {
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