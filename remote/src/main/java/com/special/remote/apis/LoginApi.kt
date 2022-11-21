package com.special.remote.apis

import com.special.domain.entities.user.LoginToken
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApi {
    @GET("/login/oauth2/id-token")
    suspend fun socialLogin(@Query("idToken") idToken: String): LoginToken

    @POST("/login/oauth2/refresh")
    suspend fun tokenRefresh(@Body request: LoginToken): LoginToken
}