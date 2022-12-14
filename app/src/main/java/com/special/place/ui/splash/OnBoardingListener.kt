package com.special.place.ui.splash

import androidx.lifecycle.LiveData

interface OnBoardingListener {
    val onBoardingList: LiveData<List<OnBoarding>>
}

data class OnBoarding(
    val idx: Int = 0,
    val content: String = "",
    val subContent: String = ""
)