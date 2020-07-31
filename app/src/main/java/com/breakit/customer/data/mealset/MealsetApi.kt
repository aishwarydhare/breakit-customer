package com.breakit.customer.data.mealset

import com.breakit.customer.model.GenericResponseBase
import com.breakit.customer.model.MealSet
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.POST

/**
 * Service to make API calls for Orders
 */
interface MealsetApi {

    /**
     * Place new order
     */
    @POST("api/v1/mealset/get")
    @Throws(Exception::class)
    suspend fun getTodayMealset(
    ): Response<GenericResponseBase<MealSet>>

    companion object {
        /**
         * Factory function for [MealsetApi]
         */
        fun create(retroFit: Retrofit): MealsetApi = retroFit.create(
            MealsetApi::class.java
        )
    }
}
