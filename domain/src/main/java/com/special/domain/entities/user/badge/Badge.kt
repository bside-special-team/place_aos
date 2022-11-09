package com.special.domain.entities.user.badge

data class Badge(
    val id: String,
    val name: String,
    val image: String,
    val type: Int,
    val isOwn: Boolean
)
