package com.special.place.ui.login

import com.special.data.usecases.LoginUseCase

interface LoginEventListener {
    companion object {
        fun empty(): LoginUseCase = object : LoginUseCase {
            override fun kakaoLogin() {}

            override fun googleLogin() {}
        }
    }
}