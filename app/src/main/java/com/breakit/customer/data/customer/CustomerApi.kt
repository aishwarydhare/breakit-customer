package com.breakit.customer.data.customer

import com.breakit.customer.model.Customer
import com.breakit.customer.model.GenericResponseBase
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.POST

/**
 * Service to make API calls for Customer
 */
interface CustomerApi {

    /**
     * Update customer details
     */
    @POST("api/v1/customer/profile/update")
    @Throws(Exception::class)
    suspend fun updateCustomerProfile(): Response<GenericResponseBase<Any>>

    /**
     * Get customer details
     */
    @POST("api/v1/customer/profile/get")
    @Throws(Exception::class)
    suspend fun getCustomerProfile(): Response<GenericResponseBase<Customer>>

    companion object {
        /**
         * Factory function for [CustomerApi]
         */
        fun create(retroFit: Retrofit): CustomerApi = retroFit.create(
            CustomerApi::class.java
        )
    }
}
