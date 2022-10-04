package com.special.data

import com.pluto.plugins.network.PlutoInterceptor
import com.special.data.repoimpl.PlaceRepoImpl
import com.special.domain.datasources.PlaceRemoteDataSource
import com.special.domain.repositories.PlaceRepository
import com.special.remote.ApiManager
import com.special.remote.impls.PlaceRemoteDataImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MockupModule {
    @Singleton
    @Provides
    fun provideApiManager(): ApiManager {
        return ApiManager(
            baseUrl = "http://115.85.181.70:8080/",
            interceptors = interceptors()
        )
    }

    private fun interceptors(): List<Interceptor> {
        return mutableListOf<Interceptor>().apply {
            if (BuildConfig.DEBUG) {
                add(PlutoInterceptor())
                add(HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.HEADERS)
                })
            }
        }
    }
}

@InstallIn(SingletonComponent::class)
@Module
abstract class RepoBinds {
    @Binds
    abstract fun bindPlaceRepo(impl: PlaceRepoImpl): PlaceRepository
}

@InstallIn(SingletonComponent::class)
@Module
abstract class RemoteBinds {
    @Binds
    abstract fun bindPlaceRemote(impl: PlaceRemoteDataImpl): PlaceRemoteDataSource
}