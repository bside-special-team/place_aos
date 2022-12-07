package com.special.place.ui.my

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.special.domain.entities.place.CommentPlace
import com.special.domain.entities.place.Place
import com.special.domain.entities.place.comment.Comment
import com.special.domain.entities.user.LevelInfo
import com.special.domain.entities.user.User
import com.special.domain.repositories.PlaceRepository
import com.special.domain.repositories.UserRepository
import com.special.place.MyPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyInformationViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val placeRepo: PlaceRepository
) : ViewModel(), MyInformationEventListener {

    private val _routePlaceDetail: MutableLiveData<Place> = MutableLiveData()
    val routePlaceDetail: LiveData<Place> = _routePlaceDetail

    private val _showDeleteCommentDialog: MutableLiveData<Boolean> = MutableLiveData()
    val showDeleteCommentDialog: LiveData<Boolean> = _showDeleteCommentDialog

    override val currentVisitedPlace: LiveData<List<Place>> = liveData { emit(userRepo.recentPlaces()) }

    override val userInfo: LiveData<User> = userRepo.currentUser.asLiveData()

    override val nextLevel: LiveData<LevelInfo> = liveData { emit(userRepo.nextLevel()) }

    override val myBookmarkPlace: LiveData<List<Place>>
        get() = MutableLiveData(listOf())

    override val myCommentPlace: Flow<PagingData<CommentPlace>>
        get() = Pager(PagingConfig(pageSize = 30)) { MyPagingSource(placeRepo::myComments) }.flow.cachedIn(viewModelScope)

    override val myRecommendPlace: Flow<PagingData<Place>>
        get() = Pager(PagingConfig(pageSize = 30)) { MyPagingSource(placeRepo::myLikePlace) }.flow.cachedIn(viewModelScope)

    override val myPlace: Flow<PagingData<Place>>
        get() = Pager(PagingConfig(pageSize = 30)) { MyPagingSource(placeRepo::myPlaces) }.flow.cachedIn(viewModelScope)

    override fun bookmarkPlace(id: String) {

    }

    override fun showPlaceDetail(place: Place) {
        placeRepo.selectPlace(place)
        _routePlaceDetail.postValue(place)
    }

    private var targetComment: Comment? = null
    override fun deleteComment(comment: Comment) {
        targetComment = comment
        _showDeleteCommentDialog.postValue(true)
    }

    fun hideDeleteCommentDialog() {
        _showDeleteCommentDialog.postValue(false)
    }

    fun doDeleteComment() {
        viewModelScope.launch {
            targetComment?.let {
                userRepo.deleteComment(it.id)
            }
        }
    }

}