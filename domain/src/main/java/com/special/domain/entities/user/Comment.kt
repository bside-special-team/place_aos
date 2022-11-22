package com.special.domain.entities.user

data class Comment(
    val id: String,
    val comment: String,
    val placeId: String,
    val createdAt: String,
    val lastModifiedAt: String,
    val user: User
)

data class CommentResponse(
    val comments: List<Comment>,
    val lastTimestamp: Long,
    val hasNext: Boolean
)

data class CommentRequest(
    val placeId: String,
    val comment: String
)