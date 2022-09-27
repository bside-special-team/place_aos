package com.special.mock.api

import com.special.mock.model.PagingResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyApi {
    @GET("EvInfoServiceV2/v1/getEvSearchList")
    suspend fun getEvChargerList(
        @Query("page") page: Int,
        @Query("perPage") size: Int,
        @Query("serviceKey") key: String
    ) : PagingResponseModel
}