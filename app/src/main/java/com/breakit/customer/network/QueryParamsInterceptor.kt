package com.breakit.customer.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QueryParamsInterceptor @Inject constructor(private var requestHelper: RequestHelper) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
        return chain.proceed(chain.request().newBuilder().url(url).build())
    }
}
