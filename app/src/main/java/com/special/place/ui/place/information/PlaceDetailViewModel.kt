package com.special.place.ui.place.information

import androidx.lifecycle.*
import coil.request.ImageRequest
import com.special.domain.entities.place.Place
import com.special.domain.repositories.PlaceRepository
import com.special.place.util.CoilRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val coilRequest: CoilRequest,
    private val placeRepo: PlaceRepository,
) : ViewModel(),
    PlaceDetailListener {

    private val _currentPlace: LiveData<Place> = placeRepo.currentPlace.asLiveData()

    private val _isBookmarked: MutableLiveData<Boolean> = MutableLiveData()
    override val isBookmarked: LiveData<Boolean>
        get() = _isBookmarked

    override val placeInfo: LiveData<Place>
        get() = _currentPlace

    override val imageList: LiveData<List<String>> = _currentPlace.map { it.imageUuids }

    private val _setBottomSheetComment: MutableLiveData<Boolean> = MutableLiveData()
    override val showBottomSheetComment: LiveData<Boolean> = _setBottomSheetComment

    private val _showBottomSheetDeletePlace: MutableLiveData<Boolean> = MutableLiveData()
    override val showBottomSheetDeletePlace: LiveData<Boolean> = _showBottomSheetDeletePlace

    override fun bookmarkPlace(id: String) {
        TODO("Not yet implemented")
    }

    override fun commentPlace(id: String) {
        TODO("Not yet implemented")
    }

    override fun recommendPlace(id: String) {
        viewModelScope.launch { placeRepo.likePlace(id) }
    }

    override fun commentBtnClick() {
        _setBottomSheetComment.postValue(true)
    }

    override fun placeDeleteBtnClick() {

//        _setBottomSheet.value = "placeDelete"
        _showBottomSheetDeletePlace.postValue(true)
    }

    override fun coilRequest(uuid: String): ImageRequest {
        return coilRequest.myImageRequest(uuid)
    }

    private var _reason: String = ""

    override fun reportReason(reason: String) {
        _reason = reason
    }

    override fun placeDeleteRequestClick() {
        val placeId = _currentPlace.value?.id
        if (placeId != null) {
            viewModelScope.launch {
                runCatching {
                    placeRepo.reportPlace(placeId, _reason)
                }.onSuccess {
                    _showBottomSheetDeletePlace.postValue(false)
                }.onFailure {
                    it.printStackTrace()
                }
            }
        }

    }

}

data class PlaceInfo(
    val id: String,
    val name: String,
    val type: String,
    val recommendCnt: Int,
    val visitCnt: Int,
    val writerName: String,
    val imageList: List<*>,
    val hashTags: List<*>
)