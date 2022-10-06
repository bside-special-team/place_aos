package com.special.place.ui.place.register.besttime

import androidx.lifecycle.LiveData

interface BestTimeEventListener {
    val placeVisitTime: LiveData<String>
    fun setPlaceBestStartTime(time: String)
    fun setPlaceBestEndTime(time: String)
}