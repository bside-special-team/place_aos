package com.special.domain.entities.user

data class PointResult(
    val user: User?,
    val levelUp: Boolean
) {
    companion object {
        fun empty() = PointResult(user = null, levelUp = false)
    }
}