package com.breakit.customer.data.customer

import androidx.annotation.MainThread
import com.breakit.customer.model.Customer
import com.breakit.customer.model.GenericResponseBase
import com.breakit.customer.utils.async.ThreadManager
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository for bookmark operations
 */
class CustomerRepository @Inject constructor(
    private val api: CustomerApi,
    private val threadManager: ThreadManager
) {

    /**
     * Fetch customer details from remote
     *
     */
    @MainThread
    suspend fun getCustomerFromRemote(): Pair<GenericResponseBase<Customer>?, Boolean> {
        return withContext(threadManager.io) {
            val response = api.getCustomerProfile()
            response.body() to response.isSuccessful
        }
    }

    /**
     * Fetch customer details from remote
     *
     */
    @MainThread
    suspend fun updateCustomerProfile(): Pair<GenericResponseBase<Any>?, Boolean> {
        return withContext(threadManager.io) {
            val response = api.updateCustomerProfile()
            response.body() to response.isSuccessful
        }
    }

}
