package com.special.domain.entities.place.comment

import com.special.domain.entities.place.Place
import com.special.domain.entities.user.User

data class Comment(
    val id: String,
    val comment: String,
    val placeId: String,
    val createdAt: String,
    val lastModifiedAt: String,
    val place: Place?,
    val user: User
) {

}

