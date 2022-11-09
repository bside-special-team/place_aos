package com.special.domain.repositories

import com.special.domain.entities.user.LevelInfo
import com.special.domain.entities.user.badge.Badge

interface CommonRepository {

    suspend fun allBadges(): List<Badge>

    suspend fun privacyPolicy(): String

    suspend fun useTerms(): String

    suspend fun allLevels(): List<LevelInfo>
}