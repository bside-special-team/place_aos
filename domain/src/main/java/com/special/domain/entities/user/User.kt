package com.special.domain.entities.user

import com.google.gson.annotations.SerializedName

/*
{
    "response":
        {
            "createdAt":null,
            "lastModifiedAt":"2022-12-03 15:02:21",
            "id":"637b27a4da111f0a76d1d070",
            "authProvider":"KAKAO",
            "subject":"2488862823",
            "email":"leejunhwa88@gmail.com",
            "nickName":"ggg",
            "visitInfos":[],
            "recPlaces":[],
            "userLevel":"LEVEL_TWO",
            "point":220,
            "userId":"637b27a4da111f0a76d1d070",
            "label":"동네주민"
        },
    "pointResult":
        {
            "user":
                {
                    "userId":"637b27a4da111f0a76d1d070",
                    "nickName":"ggg",
                    "userLevel":"LEVEL_TWO",
                    "label":"동네주민",
                    "point":220
            },
        "levelUp":false
    }
}
 */

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
            level = 0,
            progress = 0f,
            point = 0,
            label = ""
        )
    }
}

data class NickNameUpdate(val nickName: String)