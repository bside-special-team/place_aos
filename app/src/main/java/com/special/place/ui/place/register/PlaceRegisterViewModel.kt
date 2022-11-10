package com.special.place.ui.place.register

import androidx.lifecycle.*
import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.PlaceCategory
import com.special.domain.entities.place.RequestRegisterPlace
import com.special.domain.repositories.PlaceRegisterRepository
import com.special.place.ui.place.register.besttime.BestTimeEventListener
import com.special.place.ui.place.register.category.CategoryEventListener
import com.special.place.ui.place.register.input.PlaceInputEventListener
import com.special.place.ui.place.register.location.PlaceLocationEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceRegisterViewModel @Inject constructor(private val placeRegisterRepo: PlaceRegisterRepository) :
    ViewModel(), CategoryEventListener, BestTimeEventListener, PlaceInputEventListener,
    PlaceLocationEventListener {
    private val _placeRegisterResult: MutableLiveData<Result<String>> = MutableLiveData()

    val placeRegisterResult: LiveData<Result<String>> = _placeRegisterResult

    private val _placeCategories: MutableLiveData<List<PlaceCategory>> = MutableLiveData()
    override val placeCategories: LiveData<List<PlaceCategory>> = _placeCategories

    override val displayLocation: LiveData<String> = placeRegisterRepo.locationText.asLiveData()

    private val _placeRequest: MutableLiveData<RequestRegisterPlace> = MutableLiveData()

    override val placeName: LiveData<String> = _placeRequest.map { it.name ?: "" }
    override fun setPlaceName(name: String) {
        val newRequest = _placeRequest.value?.copy(name = name)

        _placeRequest.postValue(newRequest)
    }

    override val placeDescription: LiveData<String> = _placeRequest.map { "" }
    override fun setPlaceDescription(text: String) {
//        val newRequest = _placeRequest.value?.copy(description = text)
//
//        _placeRequest.postValue(newRequest)
    }

    override val placeVisitTime: LiveData<String> = _placeRequest.map {
        "선택 하여 주세요"
    }

    override fun setPlaceBestStartTime(time: String) {
//        val newRequest = _placeRequest.value?.copy(bestStartTime = time)
//
//        _placeRequest.postValue(newRequest)
    }

    override fun setPlaceBestEndTime(time: String) {
//        val newRequest = _placeRequest.value?.copy(bestEndTime = time)
//
//        _placeRequest.postValue(newRequest)
    }

    override fun setCategory(category: PlaceCategory) {
//        val newRequest = _placeRequest.value?.copy(categoryCode = category.code)
//
//        _placeRequest.postValue(newRequest)
    }

    override fun updateCameraPosition(coordinate: Coordinate) {
        // 처음으로 호출 될것을 예상하고 위치 변경 되었을 경우에만 객체를 생성 하도록 작성.
        val newRequest = _placeRequest.value?.copy(coordinate = coordinate) ?: run {
            RequestRegisterPlace(coordinate = coordinate)
        }

        _placeRequest.postValue(newRequest)
        placeRegisterRepo.updateLocation(coordinate)
    }

    fun registerPlace() {
        viewModelScope.launch {
            val request = _placeRequest.value

            if (request != null) {
                val result = placeRegisterRepo.registerPlace(request).runCatching {
                    "등록 되었습니다."
                }

                _placeRegisterResult.postValue(result)
            } else {
                // TODO: Throw Error Result
            }
        }
    }

    init {
        viewModelScope.launch {
            _placeCategories.postValue(placeRegisterRepo.categories())
        }

    }
}