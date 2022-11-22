package com.special.place.ui.place.information

import androidx.lifecycle.LiveData
import coil.request.ImageRequest
import com.special.domain.entities.place.comment.Comment

interface PlaceDetailListener {
    val placeInfo: LiveData<PlaceInfo>
    val comment: LiveData<List<Comment>>
    val isBookmarked: LiveData<Boolean>

    val setBottomSheet: LiveData<String>

    fun recommendPlace(id: String)
    fun bookmarkPlace(id: String)
    fun commentPlace(id: String)

    fun commentBtnClick()
    fun placeDeleteBtnClick()
    fun coilRequest(uuid: String): ImageRequest
    fun setPlaceInfo(placeInfo: PlaceInfo)
}