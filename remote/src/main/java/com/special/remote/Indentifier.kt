package com.special.remote

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KakaoApiManager

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PlaceAppApiManager

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PlaceLoginApiManager