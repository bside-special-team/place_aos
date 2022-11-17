package com.special.place.ui.place.register

import androidx.lifecycle.*
import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.RequestRegisterPlace
import com.special.domain.repositories.PlaceRegisterRepository
import com.special.place.ui.place.register.input.PlaceInputEventListener
import com.special.place.ui.place.register.location.PlaceLocationEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceRegisterViewModel @Inject constructor(private val placeRegisterRepo: PlaceRegisterRepository) :
    ViewModel(), PlaceInputEventListener,
    PlaceLocationEventListener {
    private val _placeRegisterResult: MutableLiveData<Result<String>> = MutableLiveData()

    val placeRegisterResult: LiveData<Result<String>> = _placeRegisterResult

    override val displayLocation: LiveData<String> = placeRegisterRepo.locationText.asLiveData()

    private val _placeRequest: MutableLiveData<RequestRegisterPlace> = MutableLiveData()

    override val placeName: LiveData<String> = _placeRequest.map { it.name ?: "" }
    override fun setPlaceName(name: String) {
        val newRequest = _placeRequest.value?.copy(name = name)

        _placeRequest.postValue(newRequest)
    }

    override fun updateCameraPosition(coordinate: Coordinate) {
        // 처음으로 호출 될것을 예상하고 위치 변경 되었을 경우에만 객체를 생성 하도록 작성.
        val newRequest = _placeRequest.value?.copy(coordinate = coordinate) ?: run {
            RequestRegisterPlace(coordinate = coordinate)
        }

        _placeRequest.postValue(newRequest)
        placeRegisterRepo.updateLocation(coordinate)
    }

    override val step: LiveData<PlaceRegisterStep>
        get() = TODO("Not yet implemented")

    override fun back() {
        TODO("Not yet implemented")
    }

    override fun next() {
        TODO("Not yet implemented")
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
//        viewModelScope.launch {
//            _placeCategories.postValue(placeRegisterRepo.categories())
//        }

    }
}