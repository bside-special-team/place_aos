package com.special.place.ui.place.register.hashtags

import androidx.lifecycle.LiveData
import com.special.place.ui.UiState
import com.special.place.ui.place.register.PlaceRegisterStep
import com.special.place.ui.place.register.RegisterEventListener

interface HashtagEventListener : RegisterEventListener {
    val hashtags: LiveData<List<String>>

    fun updateHashtag(hashtag: String)

    companion object {
        fun empty() = object : HashtagEventListener {
            override val hashtags: LiveData<List<String>>
                get() = TODO("Not yet implemented")

            override fun updateHashtag(hashtag: String) {
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