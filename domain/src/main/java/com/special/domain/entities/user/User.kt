package com.special.domain.entities.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userId")
    val id: String,
    @SerializedName("authProvider")
    val loginType: LoginType?,
    val subject: String?,
    val email: String?,
    val nickName: String?,
    val pushAlarm: Boolean = false,
    val marketingAlarm: Boolean = false,
    val myPoint: Int,
    val myBadge: String?,
    val level: Int?,
    val progress: Float?,
    val point: Int,
    val label: String
) {
    companion object {
        fun mock() = User(
            id = "1234",
            loginType = null,
            subject = null,
            email = null,
            nickName = null,
            pushAlarm = false,
            marketingAlarm = false,
            myPoint = 0,
            myBadge = null,
            level = 0,
            progress = 0f,
            point = 0,
            label = ""
        )
    }
}

data class NickNameUpdate(val nickName: String)