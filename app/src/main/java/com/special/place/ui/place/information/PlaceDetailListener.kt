package com.special.place.ui.place.information

import androidx.lifecycle.LiveData
import coil.request.ImageRequest
import com.special.domain.entities.place.CommentPlace
import com.special.domain.entities.place.Place

interface PlaceDetailListener {
    val placeInfo: LiveData<Place>
    val comments: LiveData<List<CommentPlace>>
    val isBookmarked: LiveData<Boolean>
    val imageList: LiveData<List<String>>

    val setBottomSheetComment: LiveData<String>
    val setBottomSheetDelete: LiveData<String>

    fun recommendPlace(id: String)
    fun bookmarkPlace(id: String)
    fun commentPlace(id: String)

    fun commentBtnClick()
    fun placeDeleteBtnClick()
    fun coilRequest(uuid: String): ImageRequest


    fun pickPlaceDeleteReason(idx: Int)
    fun placeDeleteRequestClick()
}