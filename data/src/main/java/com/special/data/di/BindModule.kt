package com.special.data.di

import com.pluto.plugins.network.PlutoInterceptor
import com.special.data.BuildConfig
import com.special.data.KAKAO_API_KEY
import com.special.data.repoimpl.PlaceRegisterRepoImpl
import com.special.data.repoimpl.PlaceRepoImpl
import com.special.data.repoimpl.UserRepoImpl
import com.special.domain.datasources.CoordinateToAddressDataSource
import com.special.domain.datasources.RemoteDataSource
import com.special.domain.repositories.PlaceRegisterRepository
import com.special.domain.repositories.PlaceRepository
import com.special.domain.repositories.UserRepository
import com.special.remote.ApiManager
import com.special.remote.KakaoApiKeyInterceptor
import com.special.remote.KakaoApiManager
import com.special.remote.PlaceAppApiManager
import com.special.remote.impls.CoordinateToAddressRemoteImpl
import com.special.remote.impls.RemoteDataImpl
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
    @PlaceAppApiManager
    @Singleton
    @Provides
    fun provideApiManager(): ApiManager {
        return ApiManager(
            baseUrl = "http://115.85.181.70:8080/",
            interceptors = interceptors()
        )
    }

    @KakaoApiManager
    @Singleton
    @Provides
    fun provideKakaoApiManager(): ApiManager {
        return ApiManager(
            baseUrl = "https://dapi.kakao.com/",
            interceptors = interceptors().plus(KakaoApiKeyInterceptor(KAKAO_API_KEY))
        )
    }

    private fun interceptors(): List<Interceptor> {
        return mutableListOf<Interceptor>().apply {
            if (BuildConfig.DEBUG) {
                add(PlutoInterceptor())
                add(HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
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

    @Binds
    abstract fun bindPlaceRegisterRepo(impl: PlaceRegisterRepoImpl): PlaceRegisterRepository

    @Binds
    abstract fun bindUserRepo(impl: UserRepoImpl): UserRepository
}

@InstallIn(SingletonComponent::class)
@Module
abstract class RemoteBinds {
    @Binds
    abstract fun bindPlaceRemote(impl: RemoteDataImpl): RemoteDataSource

    @Binds
    abstract fun bindCoord2AddrRemote(impl: CoordinateToAddressRemoteImpl): CoordinateToAddressDataSource
}