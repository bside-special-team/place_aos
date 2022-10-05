package com.special.place.ui.place.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.special.domain.entities.Coordinate
import com.special.domain.entities.PlaceCategory
import com.special.place.ui.place.register.besttime.BestTimeEventListener
import com.special.place.ui.place.register.category.CategoryEventListener
import com.special.place.ui.place.register.input.PlaceInputEventListener
import com.special.place.ui.place.register.location.PlaceLocationEventListener
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class PlaceRegisterViewModel : ViewModel(), CategoryEventListener, BestTimeEventListener, PlaceInputEventListener, PlaceLocationEventListener {
    private val _placeRegisterResult: MutableLiveData<Result<Unit>> = MutableLiveData()

    val placeRegisterResult: LiveData<Result<Unit>> = _placeRegisterResult

    private val _placeCategories: MutableLiveData<List<PlaceCategory>> = MutableLiveData()
    override val placeCategories: LiveData<List<PlaceCategory>> = _placeCategories

    private val _displayLocation: MutableLiveData<String> = MutableLiveData()
    override val displayLocation: LiveData<String> = _displayLocation

    private val _placeName: MutableLiveData<String> = MutableLiveData()
    override val placeName: LiveData<String> = _placeName
    override fun setPlaceName(name: String) {
        _placeName.postValue(name)
    }

    private val _placeDescription: MutableLiveData<String> = MutableLiveData()
    override val placeDescription: LiveData<String> = _placeDescription
    override fun setPlaceDescription(text: String) {
        _placeDescription.postValue(text)
    }

    private val _placeVisitTime: MutableLiveData<String> = MutableLiveData()
    override val placeVisitTime: LiveData<String> = _placeVisitTime
    override fun setPlaceVisitTime(time: String) {
        _placeVisitTime.postValue(time)
    }

    private val _placeCategory: MutableLiveData<PlaceCategory> = MutableLiveData()
    override fun setCategory(category: PlaceCategory) {
        _placeCategory.postValue(category)
    }

    override fun updateCameraPosition(coordinate: Coordinate) {

    }

    fun registerPlace() {

    }
}