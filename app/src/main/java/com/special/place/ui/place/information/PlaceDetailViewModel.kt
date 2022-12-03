package com.special.place.ui.place.information

import androidx.lifecycle.*
import coil.request.ImageRequest
import com.special.domain.entities.place.CommentPlace
import com.special.domain.entities.place.Place
import com.special.domain.entities.place.combine
import com.special.domain.repositories.PlaceRepository
import com.special.place.util.CoilRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val coilRequest: CoilRequest,
    private val placeRepo: PlaceRepository,
) : ViewModel(),
    PlaceDetailListener {

    private val _currentPlace: LiveData<Place> = placeRepo.currentPlace.asLiveData()

    override val comments: LiveData<List<CommentPlace>> = placeRepo.currentPlace.map { place ->
        placeRepo.commentList(place.id, 0).list.map {
            place.combine(it)
        }
    }.asLiveData()

    private val _isBookmarked: MutableLiveData<Boolean> = MutableLiveData()
    override val isBookmarked: LiveData<Boolean>
        get() = _isBookmarked

    override val placeInfo: LiveData<Place>
        get() = _currentPlace

    override val imageList: LiveData<List<String>> = _currentPlace.map { it.imageUuids }

    private val _setBottomSheetComment: MutableLiveData<String> = MutableLiveData()
    override val setBottomSheetComment: LiveData<String> = _setBottomSheetComment

    private val _setBottomSheetDeleteComment: MutableLiveData<String> = MutableLiveData()
    override val setBottomSheetDeleteComment: LiveData<String> = _setBottomSheetDeleteComment

    private val _setBottomSheetDeletePlace: MutableLiveData<String> = MutableLiveData()
    override val setBottomSheetDeletePlace: LiveData<String> = _setBottomSheetDeletePlace

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
        _setBottomSheetComment.postValue("comment")
    }

    override fun placeDeleteBtnClick() {

//        _setBottomSheet.value = "placeDelete"
        _setBottomSheetDeletePlace.postValue("placeDelete")
    }

    override fun coilRequest(uuid: String): ImageRequest {
        return coilRequest.myImageRequest(uuid)
    }

    override fun pickPlaceDeleteReason(idx: Int) {
        TODO("Not yet implemented")
    }

    override fun placeDeleteRequestClick() {
        TODO("Not yet implemented")
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