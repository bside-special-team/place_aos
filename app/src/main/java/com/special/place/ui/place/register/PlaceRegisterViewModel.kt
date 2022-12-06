package com.special.place.ui.place.register

import android.net.Uri
import androidx.lifecycle.*
import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.RequestRegisterPlace
import com.special.domain.repositories.PlaceRegisterRepository
import com.special.place.ui.UiState
import com.special.place.ui.place.register.hashtags.HashtagEventListener
import com.special.place.ui.place.register.input.PlaceInputEventListener
import com.special.place.ui.place.register.location.PlaceLocationEventListener
import com.special.place.ui.place.register.select.picture.SelectPictureEventListener
import com.special.place.util.ContentResolverHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceRegisterViewModel @Inject constructor(
    private val placeRegisterRepo: PlaceRegisterRepository,
    private val resolverHelper: ContentResolverHelper
) :
    ViewModel(), PlaceInputEventListener, SelectPictureEventListener, HashtagEventListener,
    PlaceLocationEventListener {

    override val displayLocation: LiveData<String> = placeRegisterRepo.locationText.asLiveData()

    private val _placeRequest: MutableLiveData<RequestRegisterPlace> = MutableLiveData()

    override val placeName: LiveData<String> = _placeRequest.map { it.name ?: "" }
    override fun setPlaceName(name: String) {
        val newRequest = _placeRequest.value?.copy(name = name)

        _placeRequest.value = newRequest
    }

    override fun updateCameraPosition(coordinate: Coordinate) {
        // 처음으로 호출 될것을 예상하고 위치 변경 되었을 경우에만 객체를 생성 하도록 작성.
        val newRequest = _placeRequest.value?.copy(coordinate = coordinate) ?: run {
            RequestRegisterPlace(coordinate = coordinate)
        }

        _placeRequest.postValue(newRequest)
        placeRegisterRepo.updateLocation(coordinate)
    }

    private val _pictures: MutableLiveData<List<Uri>> = MutableLiveData()
    override val pictures: LiveData<List<Uri>> = _pictures

    override fun selectPicture(uri: Uri) {
        val origins = pictures.value ?: listOf()
        _pictures.postValue(origins.plus(uri))
    }

    override fun unselectPicture(uri: Uri) {
        val origins = pictures.value ?: listOf()
        _pictures.postValue(origins.minus(uri))
    }

    private val _hashtags: MutableLiveData<List<String>> = MutableLiveData()
    override val hashtags: LiveData<List<String>> = _hashtags

    override fun updateHashtag(hashtag: String) {
        val origins = hashtags.value ?: listOf()
        if (origins.contains(hashtag)) {
            _hashtags.postValue(origins.minus(hashtag))
        } else {
            _hashtags.postValue(origins.plus(hashtag))
        }
    }

    private val _step: MutableLiveData<PlaceRegisterStep> =
        MutableLiveData(PlaceRegisterStep.Location)
    override val step: LiveData<PlaceRegisterStep> = _step

    override fun back() {
        val currentStep = step.value
        if (currentStep != null && currentStep != PlaceRegisterStep.Location) {
            _step.postValue(currentStep.back())
        }
    }

    override fun next() {
        val currentStep = step.value

        if (currentStep == PlaceRegisterStep.ChooseHashtag) {
            registerPlace()
        } else if (currentStep != null && currentStep != PlaceRegisterStep.Complete) {
            _step.postValue(currentStep.next())
        }
    }

    private fun registerPlace() {
        _uiState.postValue(UiState.Progress)
        viewModelScope.launch {
            val imageUUIDs = pictures.value?.mapNotNull { resolverHelper.uriToFile(it) }
                ?.let { placeRegisterRepo.uploadImage(it) }

            val request = _placeRequest.value?.copy(
                imageUuids = imageUUIDs ?: listOf(),
                hashTags = hashtags.value ?: listOf()
            )

            if (request != null) {
                placeRegisterRepo.registerPlace(request).runCatching {
                    _uiState.postValue(UiState.Done)
                }.onFailure {
                    _uiState.postValue(UiState.Error(it))
                }
            } else {
                _uiState.postValue(UiState.Error(IllegalArgumentException()))
            }
        }
    }

    private val _uiState: MutableLiveData<UiState> = MutableLiveData()
    override val uiState: LiveData<UiState> = _uiState
}