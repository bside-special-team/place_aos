package com.special.place.ui.place.information.comment

import androidx.lifecycle.LiveData
import com.special.place.ui.UiState

interface CommentRegisterEventListener {
    fun registerComment(comment: String)
    val commentResult: LiveData<UiState>
}