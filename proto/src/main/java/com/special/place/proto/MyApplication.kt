package com.special.place.proto

import android.app.Application
import com.special.place.resource.R
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Initializer.initPluto(this)



//        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }


}