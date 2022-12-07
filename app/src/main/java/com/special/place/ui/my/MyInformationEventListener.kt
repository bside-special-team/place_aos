package com.special.place.ui.my

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.special.domain.entities.place.CommentPlace
import com.special.domain.entities.place.Place
import com.special.domain.entities.place.comment.Comment
import com.special.domain.entities.user.LevelInfo
import com.special.domain.entities.user.User
import kotlinx.coroutines.flow.Flow

interface MyInformationEventListener {
    val userInfo: LiveData<User>
    val nextLevel: LiveData<LevelInfo>

    val currentVisitedPlace: LiveData<List<Place>>
    val myCommentPlace: Flow<PagingData<CommentPlace>>
    val myRecommendPlace: Flow<PagingData<Place>>
    val myBookmarkPlace: LiveData<List<Place>>
    val myPlace: Flow<PagingData<Place>>

//    val isBookmarked: LiveData<Boolean>

    fun bookmarkPlace(id: String)

    fun showPlaceDetail(place: Place)
    fun deleteComment(comment: Comment)
}