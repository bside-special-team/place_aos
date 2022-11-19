package com.special.place.ui.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.special.domain.entities.place.CommentPlace
import com.special.domain.entities.place.Place
import com.special.domain.entities.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyInformationViewModel @Inject constructor() : ViewModel(), MyInformationEventListener {
    override val currentVisitedPlace: LiveData<List<Place>>
        get() = TODO("Not yet implemented")
    override val isBookmarked: LiveData<Boolean>
        get() = TODO("Not yet implemented")
    override val userInfo: LiveData<User>
        get() = TODO("Not yet implemented")
    override val myBookmarkPlace: LiveData<List<Place>>
        get() = TODO("Not yet implemented")
    override val myCommentPlace: LiveData<List<CommentPlace>>
        get() = TODO("Not yet implemented")
    override val myRecommendPlace: LiveData<List<Place>>
        get() = TODO("Not yet implemented")
    override val myPlace: LiveData<List<Place>>
        get() = TODO("Not yet implemented")

    override fun bookmarkPlace(id: String) {
        TODO("Not yet implemented")
    }


}