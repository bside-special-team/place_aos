package com.special.data

import com.special.mock.ApiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MockupModule {
    @Singleton
    @Provides
    fun provideApiManager(): ApiManager {
        return ApiManager(baseUrl = "https://api.odcloud.kr/api/")
    }
}