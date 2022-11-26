package com.special.place.ui

sealed class UiState {
    object Init : UiState()
    object Progress : UiState()
    object Done : UiState()
    class Error(val exception: Throwable?) : UiState()
}


