package com.special.domain.entities.place.comment

data class CommentRequest(
    val placeId: String,
    val comment: String
)

data class CommentModifyRequest(
    val commentId: String,
    val comment: String? = null
)
