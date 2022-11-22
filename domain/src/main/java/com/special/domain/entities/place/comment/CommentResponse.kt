package com.special.domain.entities.place.comment

data class CommentResponse(
    val comments: List<Comment>,
    val lastTimestamp: Long,
    val hasNext: Boolean
)
