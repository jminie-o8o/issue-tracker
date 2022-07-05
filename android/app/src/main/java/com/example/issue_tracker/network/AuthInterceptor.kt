package com.example.issue_tracker.network

import com.example.issue_tracker.AppSession
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val requestBuilder = chain.request()
            .newBuilder()

        AppSession.jwt?.let {
            requestBuilder.addHeader("authorization", it.accessToken)
        }

        return chain.proceed(requestBuilder.build())
    }
}