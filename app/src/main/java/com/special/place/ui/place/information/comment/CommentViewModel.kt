package com.special.place.ui.place.information.comment

import androidx.lifecycle.*
import com.special.domain.entities.place.CommentPlace
import com.special.domain.entities.place.Place
import com.special.domain.entities.user.User
import com.special.domain.repositories.PlaceRepository
import com.special.domain.repositories.UserRepository
import com.special.place.ui.UiState
import com.special.place.ui.my.act.CommentEventListener
import com.special.place.ui.place.information.BottomSheetType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val placeRepo: PlaceRepository,
    private val userRepo: UserRepository
) : ViewModel(), CommentEventListener {
    private val userInfo: LiveData<User> = userRepo.currentUser.asLiveData()

    private val _targetComment: MutableLiveData<CommentPlace> = MutableLiveData()
    override val targetComment: LiveData<CommentPlace> = _targetComment

    override fun isMyComment(comment: CommentPlace): Boolean {
        _targetComment.postValue(comment)
        return userInfo.value?.id == comment.comment.user.id
    }

    private val _commentResult: MutableLiveData<UiState> = MutableLiveData()
    override val commentResult: LiveData<UiState> = _commentResult

    private val _currentPlace: LiveData<Place> = placeRepo.currentPlace.asLiveData()
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

    private val _showDeleteCommentDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    override val showDeleteCommentDialog: LiveData<Boolean> = _showDeleteCommentDialog

    private val _showBottomSheet: MutableLiveData<BottomSheetType> = MutableLiveData()
    override val showBottomSheetCommentModify: LiveData<Boolean> = _showBottomSheet.map { it == BottomSheetType.ModifyComment }
    override val showBottomSheetReportComment: LiveData<Boolean> = _showBottomSheet.map { it == BottomSheetType.ReportComment }

    override fun checkDeleteComment() {
        val comment = _targetComment.value?.comment
        if (comment?.user?.id == userInfo.value?.id) {
            _showDeleteCommentDialog.postValue(true)
        }
    }

    override fun checkModifyComment() {
        val comment = _targetComment.value?.comment
        if (comment?.user?.id == userInfo.value?.id) {
            _showBottomSheet.postValue(BottomSheetType.ModifyComment)
        }
    }

    override fun reportComment() {
        _showBottomSheet.postValue(BottomSheetType.ReportComment)
    }

    override fun doDeleteComment() {
        _showDeleteCommentDialog.postValue(false)

        val targetComment = _targetComment.value?.comment
        if (targetComment != null) {
            viewModelScope.launch {
                userRepo.deleteComment(targetComment.id)
            }
        }

    }

    override fun doModifyComment(comment: String) {
        _showDeleteCommentDialog.postValue(false)

        val targetComment = _targetComment.value?.comment
        if (targetComment != null) {
            viewModelScope.launch {
                userRepo.modifyComment(targetComment.id, comment)
            }
        }
    }

    override fun hideDeleteCommentDialog() {
        _showDeleteCommentDialog.postValue(false)
    }
}