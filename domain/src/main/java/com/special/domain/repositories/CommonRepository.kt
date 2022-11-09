package com.special.domain.repositories

import com.special.domain.entities.user.badge.Badge

interface CommonRepository {

    suspend fun badgeList(): List<Badge>

    suspend fun privacyPolicy(): String

    suspend fun useTerms(): String


}