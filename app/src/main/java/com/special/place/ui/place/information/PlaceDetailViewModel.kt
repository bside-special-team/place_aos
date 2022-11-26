package com.special.place.ui.place.information

import androidx.lifecycle.*
import coil.request.ImageRequest
import com.special.domain.entities.place.Place
import com.special.domain.entities.place.comment.Comment
import com.special.domain.repositories.PlaceRepository
import com.special.place.ui.UiState
import com.special.place.ui.place.information.comment.CommentRegisterEventListener
import com.special.place.util.CoilRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val coilRequest: CoilRequest,
    private val placeRepo: PlaceRepository,
) : ViewModel(),
    PlaceDetailListener, CommentRegisterEventListener {

    private val _currentPlace: LiveData<Place> = placeRepo.currentPlace.asLiveData()

    private val _comment: MutableLiveData<List<Comment>> = MutableLiveData()
    override val comment: LiveData<List<Comment>>
        get() = _comment

    private val _isBookmarked: MutableLiveData<Boolean> = MutableLiveData()
    override val isBookmarked: LiveData<Boolean>
        get() = _isBookmarked

    override val placeInfo: LiveData<Place>
        get() = _currentPlace

    override val imageList: LiveData<List<String>> = _currentPlace.map { it.imageUuids }

    private val _setBottomSheetComment: MutableLiveData<String> = MutableLiveData()
    override val setBottomSheetComment: LiveData<String> = _setBottomSheetComment

    private val _setBottomSheetDelete: MutableLiveData<String> = MutableLiveData()
    override val setBottomSheetDelete: LiveData<String> = _setBottomSheetDelete
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
        _setBottomSheetDelete.postValue("placeDelete")
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

    private val _commentResult: MutableLiveData<UiState> = MutableLiveData()
    override val commentResult: LiveData<UiState> = _commentResult

    override fun registerComment(comment: String) {
        val targetId = _currentPlace.value?.id

        if (targetId == null || comment.isBlank()) {
            _commentResult.postValue(UiState.Error(IllegalArgumentException()))
            return
        }

        viewModelScope.launch {
            _commentResult.postValue(UiState.Progress)
            runCatching { placeRepo.registerComment(targetId = targetId, comment = comment) }.onFailure {
                _commentResult.postValue(UiState.Error(it))
            }.onSuccess {
                _commentResult.postValue(UiState.Done)
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