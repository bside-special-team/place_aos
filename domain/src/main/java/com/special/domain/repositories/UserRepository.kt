package com.special.domain.repositories

import com.special.domain.entities.user.LoginStatus
import com.special.domain.entities.user.SocialLoginResponse
import com.special.domain.entities.user.badge.Badge
import kotlinx.coroutines.flow.MutableSharedFlow

interface UserRepository {
    val loginStatus: MutableSharedFlow<LoginStatus>

    suspend fun socialLogin(response: SocialLoginResponse)

    suspend fun unregister()

    suspend fun myBadges(): List<Badge>

    suspend fun logout()

    suspend fun updatePushAlarm(enable: Boolean)

    suspend fun updateMarketingAlarm(enable: Boolean)

    suspend fun modifyNickName(nickName: String)

}