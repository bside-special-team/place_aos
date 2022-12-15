package com.special.place.ui.place.information.comment

import android.util.Log
import androidx.lifecycle.*
import com.special.domain.entities.place.CommentPlace
import com.special.domain.entities.place.combine
import com.special.domain.entities.user.User
import com.special.domain.repositories.PlaceRepository
import com.special.domain.repositories.UserRepository
import com.special.place.ui.UiState
import com.special.place.ui.my.act.CommentEventListener
import com.special.place.ui.place.information.BottomSheetType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
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

    override fun isMyComment(comment: CommentPlace): LiveData<Boolean> {
        _targetComment.postValue(comment)
        return userInfo.map { it.id == comment.comment.user.id }
    }

    private val _commentResult: MutableLiveData<UiState> = MutableLiveData()
    override val commentResult: LiveData<UiState> = _commentResult

    private val _comments: MutableLiveData<List<CommentPlace>> = MutableLiveData()
    override val comments: LiveData<List<CommentPlace>> = _comments

    init {
        viewModelScope.launch {
            placeRepo.currentPlace.collectIndexed { _, place ->
                val list = placeRepo.commentList(place.id, 0).list.map {
                    place.combine(it)
                }

                _comments.postValue(list)
            }
        }

    }

    private fun loadComments() {
        viewModelScope.launch {
            placeRepo.currentPlace.collectLatest { place ->
                val list = placeRepo.commentList(place.id, 0).list.map {
                    place.combine(it)
                }

                _comments.postValue(list)
            }
        }
    }

    override fun registerComment(comment: String) {
        _commentResult.postValue(UiState.Progress)

        viewModelScope.launch {
            placeRepo.currentPlace.collectLatest { place ->
                if (comment.isBlank()) {
                    Log.d("commentVM", "place ID :: ${place.id}")
                    _commentResult.postValue(UiState.Error(IllegalArgumentException()))
                    return@collectLatest
                }

                runCatching { placeRepo.registerComment(targetId = place.id, comment = comment) }.onFailure {
                    _commentResult.postValue(UiState.Error(it))
                }.onSuccess {
                    loadComments()
                    _commentResult.postValue(UiState.Done)
                }
            }
        }
    }

    private val _showDeleteCommentDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    override val showDeleteCommentDialog: LiveData<Boolean> = _showDeleteCommentDialog

    private val _showBottomSheet: MutableLiveData<BottomSheetType> = MutableLiveData()
    override val showBottomSheetCommentModify: LiveData<Boolean> = _showBottomSheet.map { it == BottomSheetType.ModifyComment }
    override val showBottomSheetReportComment: LiveData<Boolean> = _showBottomSheet.map { it == BottomSheetType.ReportComment }
    override val hideBottomSheet: LiveData<Boolean> = _showBottomSheet.map { it == BottomSheetType.Close }

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

    override fun reportComment(comment: CommentPlace) {
        _targetComment.postValue(comment)
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

    private var _reason: String = ""
    override fun reportReason(reason: String) {
        _reason = reason
    }

    override fun commentDeleteRequestClick() {
        val commentId = _targetComment.value?.comment?.id
        if (commentId != null) {
            viewModelScope.launch {
                runCatching {
                    placeRepo.reportComment(commentId, _reason)
                }.onSuccess {
                    _showBottomSheet.postValue(BottomSheetType.Close)
                }.onFailure {
                    it.printStackTrace()
                }

            }
        }
    }
}