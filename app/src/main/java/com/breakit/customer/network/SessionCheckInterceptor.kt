package com.breakit.customer.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionCheckInterceptor @Inject constructor() :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
        val response = chain.proceed(chain.request().newBuilder().url(url).build())
        if (response.code == 401) {

        }
        return response
    }
}
