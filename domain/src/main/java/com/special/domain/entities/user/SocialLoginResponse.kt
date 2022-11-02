package com.special.domain.entities.user

class SocialLoginResponse private constructor(val isLogin: Boolean, val type: LoginType, val idToken: String? = null) {

    companion object {
        fun success(type: LoginType, idToken: String): SocialLoginResponse {
            return SocialLoginResponse(
                isLogin = true,
                type = type,
                idToken = idToken
            )
        }

        fun notLogin(): SocialLoginResponse = SocialLoginResponse(isLogin = false, type = LoginType.None)
    }
}
