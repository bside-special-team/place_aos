package com.special.place.ui.my

import androidx.lifecycle.*
import com.special.domain.entities.place.CommentPlace
import com.special.domain.entities.place.Place
import com.special.domain.entities.user.User
import com.special.domain.repositories.UserRepository
import com.special.place.ui.my.act.CommentDeleteEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyInformationViewModel @Inject constructor(
    private val userRepo: UserRepository
) : ViewModel(), MyInformationEventListener, CommentDeleteEventListener {

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

    private val _deleteTargetComment: MutableLiveData<CommentPlace> = MutableLiveData()
    private val _showDeleteCommentDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    override val showDeleteCommentDialog: LiveData<Boolean> = _showDeleteCommentDialog

    override val showReportComment: LiveData<Boolean>
        get() = TODO("Not yet implemented")

    override fun checkDeleteComment(comment: CommentPlace) {
        if (comment.comment.user.id == userInfo.value?.id) {
            _showDeleteCommentDialog.postValue(true)
            _deleteTargetComment.postValue(comment)
        }
    }

    override fun doDeleteComment() {
        _showDeleteCommentDialog.postValue(false)

        val targetComment = _deleteTargetComment.value?.comment
        if (targetComment != null) {
            viewModelScope.launch {
                userRepo.deleteComment(targetComment.id)
            }
        }

    }


}