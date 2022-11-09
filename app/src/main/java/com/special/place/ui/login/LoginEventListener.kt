package com.special.place.ui.login

interface LoginEventListener {
    fun doKakaoLogin()
    fun doGoogleLogin()

    companion object {
        fun empty(): LoginEventListener = object : LoginEventListener {
            override fun doKakaoLogin() {}

            override fun doGoogleLogin() {}
        }
    }
}