package com.breakit.customer.data.mealset

import androidx.annotation.MainThread
import com.breakit.customer.model.GenericResponseBase
import com.breakit.customer.model.MealSet
import com.breakit.customer.utils.async.ThreadManager
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository for bookmark operations
 */
class MealsetRepository @Inject constructor(
    private val api: MealsetApi,
    private val threadManager: ThreadManager
) {

    /**
     * Place order
     *
     */
    @MainThread
    suspend fun getTodayMealset(): Pair<GenericResponseBase<MealSet>?, Boolean> {
        return withContext(threadManager.io) {
            val response = api.getTodayMealset()
            response.body() to response.isSuccessful
        }
    }
}
