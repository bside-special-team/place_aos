package com.special.place

import android.app.Activity
import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.naver.maps.map.LocationSource
import com.naver.maps.map.util.FusedLocationSource
import com.special.domain.exception.ExceptionListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent

const val LOCATION_PERMISSION_REQUEST_CODE = 0xfefefe

@InstallIn(ActivityComponent::class)
@Module
object MapModule {
    @Provides
    fun provideLocationSource(@ActivityContext context: Context): LocationSource {
        return FusedLocationSource((context as Activity), LOCATION_PERMISSION_REQUEST_CODE)
    }
}

@InstallIn(SingletonComponent::class)
@Module
object ExceptionListenerModule {
    @Provides
    fun provideExceptionListener(): ExceptionListener {
        return object : ExceptionListener {
            private val crashlytics = FirebaseCrashlytics.getInstance()
            override fun updateException(e: Throwable) {
                crashlytics.recordException(e)
            }

            override fun updateMessage(message: String) {
                crashlytics.log(message)
            }

        }
    }
}
