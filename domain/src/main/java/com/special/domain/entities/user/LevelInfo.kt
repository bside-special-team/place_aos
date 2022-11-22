package com.special.domain.entities.user

data class LevelInfo(
    val level: Int,
    val label: String,
    val minPoint: Int,
) {
    companion object {
        fun none() = LevelInfo(
            level = 0, label = "NONE", minPoint = -1
        )
    }
}
