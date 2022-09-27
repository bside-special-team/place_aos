package com.special.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.overlay.Marker
import com.special.mock.ChargerMockDataImpl
import com.special.mock.model.EvCharger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChargerViewModel @Inject constructor(private val chargerData: ChargerMockDataImpl): ViewModel() {
    private val _chargerList : MutableLiveData<List<EvCharger>> = MutableLiveData()
    val chargerList: LiveData<List<EvCharger>> = _chargerList

    init {
        viewModelScope.launch {
            val result = chargerData.getChargers(1, 1000)

            if (result.isSuccess) {
                _chargerList.postValue(result.getOrNull())
            }
        }
    }

}

fun EvCharger.toMarker() : Marker {
    return Marker().apply {
        position = LatLng(latitude.toDouble(), longitude.toDouble())
        captionText = csNm
        captionRequestedWidth = 200

        setCaptionAligns(Align.Top)
    }
}

fun EvCharger.toLatLnt(): LatLng {
    return LatLng(latitude.toDouble(), longitude.toDouble())
}