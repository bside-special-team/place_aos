package com.special.place.ui.login

interface LoginEventListener {
    fun logout()
    fun getNickname(): String?

    companion object {
        fun empty() = object : LoginEventListener {
            override fun logout() {

            }

            override fun getNickname(): String? = null

        }
    }
}