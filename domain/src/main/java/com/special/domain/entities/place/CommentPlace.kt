package com.special.domain.entities.place

import com.special.domain.entities.place.comment.Comment

data class CommentPlace(
    val id: String,
    val placeType: PlaceType,
    val coordinate: Coordinate,
    val name: String,
    val imageUrls: List<String>,
    val visitCount: Int,
    val hashTags: List<String>,
    val createdAt: String,
    val lastModifiedAt: String,
    val comment: Comment
)