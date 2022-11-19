package com.special.remote

import com.special.domain.datasources.LoginRemoteDataSource
import com.special.domain.datasources.TokenDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject


class TokenInjectInterceptor @Inject constructor(
    private val tokenData: TokenDataSource,
    private val loginRemote: LoginRemoteDataSource
) :
    Interceptor {
    final val notInjectedPath = listOf("login")

    override fun intercept(chain: Interceptor.Chain): Response {
        return checkResponse(chain)
    }

    private fun refreshRequest(chain: Interceptor.Chain) {
        val originUrl = chain.request().url()
        val newToken = runBlocking {
            val oldToken = tokenData.tokenRaw()

            println("oldToken ::: $oldToken")

            loginRemote.refreshToken(oldToken)
        }

        tokenData.updateToken(newToken)
    }

    private fun checkResponse(chain: Interceptor.Chain): Response {
        val request = injectToken(chain.request())
        val response = chain.proceed(request)

        if (response.code() == 401) {
            refreshRequest(chain)

            val newRequest = injectToken(request)

            return chain.proceed(newRequest)
        }

        return response
    }

    private fun injectToken(originRequest: Request): Request {
        println("origin path ::: ${originRequest.url().encodedPath()}")

        if (originRequest.url().encodedPath().contains("login")) {
            return originRequest
        }

        return originRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${tokenData.accessToken()}")
            .build()
    }
}