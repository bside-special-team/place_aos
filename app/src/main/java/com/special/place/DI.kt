package com.special.place

import android.app.Activity
import android.content.Context
import com.naver.maps.map.LocationSource
import com.naver.maps.map.util.FusedLocationSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

const val LOCATION_PERMISSION_REQUEST_CODE = 0xfefefe

@InstallIn(ActivityComponent::class)
@Module
object MapModule {
    @Provides
    fun provideLocationSource(@ActivityContext context: Context): LocationSource {
        return FusedLocationSource((context as Activity), LOCATION_PERMISSION_REQUEST_CODE)
    }
}