package com.special.domain.repositories

import com.special.domain.entities.Paging
import com.special.domain.entities.place.comment.Comment

interface CommentRepository {

    suspend fun myCommentList(page: Int): Paging<Comment>

    suspend fun reportComment(commentId: String)

    suspend fun commentWrite(text: String)

    suspend fun modifyComment(text: String)
}