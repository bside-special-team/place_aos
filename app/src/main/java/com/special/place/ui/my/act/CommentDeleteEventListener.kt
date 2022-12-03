package com.special.place.ui.my.act

import androidx.lifecycle.LiveData
import com.special.domain.entities.place.CommentPlace

interface CommentDeleteEventListener {
    val showDeleteCommentDialog: LiveData<Boolean>
    val showReportComment: LiveData<Boolean>

    fun checkDeleteComment(comment: CommentPlace)

    fun doDeleteComment()
}