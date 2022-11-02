package com.special.data.utils

import android.content.Context
import android.content.SharedPreferences
import com.special.domain.entities.user.LoginType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsHelper @Inject constructor(@ApplicationContext context: Context) {
    private val pref: SharedPreferences by lazy {
        context.getSharedPreferences(
            "${context.packageName}_preferences",
            Context.MODE_PRIVATE
        )
    }

    // TODO: need secure
    var accessToken: String?
        get() = pref.getString(ACCESS_TOKEN, null)
        set(value) = pref.edit().run {
            putString(ACCESS_TOKEN, value)
            apply()
        }

    // TODO: need secure
    var refreshToken: String?
        get() = pref.getString(REFRESH_TOKEN, null)
        set(value) = pref.edit().run {
            putString(REFRESH_TOKEN, value)
            apply()
        }

    var isLogin: Boolean
        get() = pref.getBoolean(IS_LOGIN, false)
        set(value) = pref.edit().run {
            putBoolean(IS_LOGIN, value)
            apply()
        }

    // TODO: need secure
    var loginType: LoginType
        get() = pref.getString(LOGIN_TYPE, null).run {
            LoginType.valueOf(this ?: "None")
        }
        set(value) = pref.edit().run {
            putString(LOGIN_TYPE, value.toString())
            apply()
        }

    var shownOnBoarding: Boolean
        get() = pref.getBoolean(SHOWN_ON_BOARDING, false)
        set(value) = pref.edit().run {
            putBoolean(SHOWN_ON_BOARDING, value)
            apply()
        }

    var tourStartTimestamp: Long
        get() = pref.getLong(TOUR_START_TIMESTAMP, -1)
        set(value) = pref.edit().run {
            putLong(TOUR_START_TIMESTAMP, value)
            apply()
        }


}

private const val ACCESS_TOKEN = "key_access_token"
private const val REFRESH_TOKEN = "key_refresh_token"
private const val IS_LOGIN = "key_is_login"
private const val LOGIN_TYPE = "key_login_type"

private const val SHOWN_ON_BOARDING = "key_shown_on_boarding"

private const val TOUR_START_TIMESTAMP = "key_tour_start_timestamp"