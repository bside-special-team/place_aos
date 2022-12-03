package com.special.domain.repositories

import com.special.domain.entities.place.Place
import com.special.domain.entities.user.LevelInfo
import com.special.domain.entities.user.LoginStatus
import com.special.domain.entities.user.SocialLoginResponse
import com.special.domain.entities.user.User
import com.special.domain.entities.user.badge.Badge
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val loginStatus: Flow<LoginStatus>

    fun currentUser(): User?

    suspend fun socialLogin(response: SocialLoginResponse)

    suspend fun unregister()

    suspend fun myBadges(): List<Badge>

    suspend fun logout()

    suspend fun updatePushAlarm(enable: Boolean)

    suspend fun updateMarketingAlarm(enable: Boolean)

    suspend fun modifyNickName(nickName: String)

    suspend fun nextLevel(): LevelInfo

    suspend fun deleteComment(commentId: String)
    suspend fun modifyComment(commentId: String, comment: String)

    suspend fun recentPlaces(): List<Place>
}