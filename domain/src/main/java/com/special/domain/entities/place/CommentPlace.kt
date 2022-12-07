package com.special.domain.entities.place

import com.special.domain.entities.place.comment.Comment

data class CommentPlace(
    val id: String,
    val placeType: PlaceType,
    val name: String,
    val hashTags: List<String>,
    val createdAt: String,
    val lastModifiedAt: String,
    val comment: Comment
)

fun Place.combine(comment: Comment) = CommentPlace(
    id = id,
    placeType = placeType,
    name = name,
    hashTags = hashTags,
    createdAt = comment.createdAt,
    lastModifiedAt = comment.lastModifiedAt,
    comment = comment
)

fun Comment.convert() = CommentPlace(
    id = place?.id ?: "",
    placeType = place?.placeType ?: PlaceType.Hidden,
    name = place?.name ?: "-",
    hashTags = place?.hashTags ?: listOf(),
    createdAt = createdAt,
    lastModifiedAt = lastModifiedAt,
    comment = this
)