package com.special.place.ui.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.special.domain.entities.place.CommentPlace
import com.special.domain.entities.place.Place
import com.special.domain.entities.user.User
import com.special.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyInformationViewModel @Inject constructor(
    private val userRepo: UserRepository
) : ViewModel(), MyInformationEventListener {

    override val currentVisitedPlace: LiveData<List<Place>>
        get() = MutableLiveData(listOf())

    override val userInfo: LiveData<User> = liveData {
        userRepo.currentUser()?.let {
            emit(it)
        }
    }

    override val myBookmarkPlace: LiveData<List<Place>>
        get() = MutableLiveData(listOf())

    override val myCommentPlace: LiveData<List<CommentPlace>>
        get() = MutableLiveData(listOf())

    override val myRecommendPlace: LiveData<List<Place>>
        get() = MutableLiveData(listOf())

    override val myPlace: LiveData<List<Place>>
        get() = MutableLiveData(listOf())

    override fun bookmarkPlace(id: String) {

    }


}