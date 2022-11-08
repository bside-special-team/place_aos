package com.special.domain.repositories

import com.special.domain.entities.user.LoginStatus
import kotlinx.coroutines.flow.MutableSharedFlow

interface UserRepository {
    val loginStatus: MutableSharedFlow<LoginStatus>

    fun kakaoLogin()
    fun googleLogin()
}