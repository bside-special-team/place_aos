package com.special.place

import Initializer
import android.app.Application
import com.special.data.social.SocialLoginHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Initializer.initPluto(this)
        SocialLoginHelper.appInitial(this)
    }


}