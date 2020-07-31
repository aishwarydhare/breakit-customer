package com.breakit.customer.data.order

import com.breakit.customer.model.GenericResponseBase
import com.breakit.customer.model.Order
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Service to make API calls for Orders
 */
interface OrderApi {

    /**
     * Get today's order
     */
    @POST("api/v1/order/today")
    @Throws(Exception::class)
    suspend fun todaysOrder(): Response<GenericResponseBase<Order>>

    /**
     * Get next order of user
     */
    @POST("api/v1/order/next")
    @Throws(Exception::class)
    suspend fun nextOrder(): Response<GenericResponseBase<Order>>

    /**
     * Place new order
     */
    @FormUrlEncoded
    @POST("api/v1/order/place")
    @Throws(Exception::class)
    suspend fun placeOrder(
        @FieldMap payload: Map<String, String>
    ): Response<GenericResponseBase<Int>>

    /**
     * Modify a placed order
     */
    @FormUrlEncoded
    @POST("api/v1/order/modify")
    @Throws(Exception::class)
    suspend fun modifyOrder(
        @FieldMap payload: Map<String, String>
    ): Response<GenericResponseBase<Any>>

    /**
     * Give rating to a order
     */
    @FormUrlEncoded
    @POST("api/v1/order/rate")
    @Throws(Exception::class)
    suspend fun rateOrder(
        @FieldMap payload: Map<String, String>
    ): Response<GenericResponseBase<Any>>

    /**
     * Get an orders details
     */
    @FormUrlEncoded
    @POST("api/v1/order/get")
    @Throws(Exception::class)
    suspend fun getOrder(
        @FieldMap payload: Map<String, String>
    ): Response<GenericResponseBase<Order>>

    /**
     * Fetch list of all past orders
     */
    @POST("api/v1/order/list")
    @Throws(Exception::class)
    suspend fun listOrderHistory(): Response<GenericResponseBase<List<Order>>>

    /**
     * Cancel an ongoing active order
     */
    @FormUrlEncoded
    @POST("api/v1/order/cancel")
    @Throws(Exception::class)
    suspend fun cancelOrder(
        @FieldMap payload: Map<String, String>
    ): Response<GenericResponseBase<Any>>

    companion object {
        /**
         * Factory function for [OrderApi]
         */
        fun create(retroFit: Retrofit): OrderApi = retroFit.create(
            OrderApi::class.java
        )
    }
}
