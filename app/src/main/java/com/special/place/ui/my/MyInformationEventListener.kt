package com.special.place.ui.my

import androidx.lifecycle.LiveData
import com.special.domain.entities.place.CommentPlace
import com.special.domain.entities.place.Place
import com.special.domain.entities.user.LevelInfo
import com.special.domain.entities.user.User

interface MyInformationEventListener {
    val userInfo: LiveData<User>
    val nextLevel: LiveData<LevelInfo>

    val currentVisitedPlace: LiveData<List<Place>>
    val myCommentPlace: LiveData<List<CommentPlace>>
    val myRecommendPlace: LiveData<List<Place>>
    val myBookmarkPlace: LiveData<List<Place>>
    val myPlace: LiveData<List<Place>>

//    val isBookmarked: LiveData<Boolean>

    fun bookmarkPlace(id: String)

}