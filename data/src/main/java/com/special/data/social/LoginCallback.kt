package com.special.data.social

import com.special.domain.entities.user.SocialLoginResponse

interface LoginCallback {
    fun onResponse(response: SocialLoginResponse)
}