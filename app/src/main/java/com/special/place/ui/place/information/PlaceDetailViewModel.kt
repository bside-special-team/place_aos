package com.special.place.ui.place.information

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.request.ImageRequest
import com.special.domain.entities.place.comment.Comment
import com.special.place.util.CoilRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(private val coilRequest: CoilRequest) : ViewModel(),
    PlaceDetailListener {

    private val _comment: MutableLiveData<List<Comment>> = MutableLiveData()
    override val comment: LiveData<List<Comment>>
        get() = _comment

    private val _isBookmarked: MutableLiveData<Boolean> = MutableLiveData()
    override val isBookmarked: LiveData<Boolean>
        get() = _isBookmarked

    private val _placeInfo: MutableLiveData<PlaceInfo> = MutableLiveData()
    override val placeInfo: LiveData<PlaceInfo>
        get() = _placeInfo

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
        TODO("Not yet implemented")
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

    override fun setPlaceInfo(placeInfo: PlaceInfo) {
        _placeInfo.postValue(placeInfo)
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