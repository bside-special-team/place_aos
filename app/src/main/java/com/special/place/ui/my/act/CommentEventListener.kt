package com.special.place.ui.my.act

import androidx.lifecycle.LiveData
import com.special.domain.entities.place.CommentPlace
import com.special.place.ui.UiState

interface CommentEventListener {
    fun registerComment(comment: String)

    val commentResult: LiveData<UiState>

    val targetComment: LiveData<CommentPlace>

    val showDeleteCommentDialog: LiveData<Boolean>
    val showBottomSheetReportComment: LiveData<Boolean>
    val showBottomSheetCommentModify: LiveData<Boolean>

    fun isMyComment(comment: CommentPlace): Boolean

    fun checkDeleteComment()
    fun checkModifyComment()

    fun reportComment()

    fun hideDeleteCommentDialog()

    fun doDeleteComment()
    fun doModifyComment(comment: String)
}