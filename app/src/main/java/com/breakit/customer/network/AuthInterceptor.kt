package com.breakit.customer.network

interface AuthInterceptor {
    fun updateAuthToken(apiToken: String)
    fun clear()
}
