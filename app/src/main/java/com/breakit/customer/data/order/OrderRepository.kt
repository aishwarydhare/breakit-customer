package com.breakit.customer.data.order

import androidx.annotation.MainThread
import com.breakit.customer.model.GenericResponseBase
import com.breakit.customer.model.Order
import com.breakit.customer.utils.async.ThreadManager
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository for bookmark operations
 */
class OrderRepository @Inject constructor(
    private val api: OrderApi,
    private val threadManager: ThreadManager
) {

    /**
     * Get todays order of user
     *
     */
    @MainThread
    suspend fun getTodaysOrder(): Pair<GenericResponseBase<Order>?, Boolean> {
        return withContext(threadManager.io) {
            val response = api.todaysOrder()
            response.body() to response.isSuccessful
        }
    }

    /**
     * Get next booking by the user
     *
     */
    @MainThread
    suspend fun getNextBooking(): Pair<GenericResponseBase<Order>?, Boolean> {
        return withContext(threadManager.io) {
            val response = api.nextOrder()
            response.body() to response.isSuccessful
        }
    }

    /**
     * Place order
     *
     */
    @MainThread
    suspend fun placeOrder(
        mealsetId: Int,
        foodItemId: Int,
        addonsCsv: String,
        addressLat: String,
        addressLon: String,
        fullAddress: String,
        addressLandmark: String,
        isDefaultAddress: Boolean
    ): Pair<GenericResponseBase<Int>?, Boolean> {
        return withContext(threadManager.io) {
            val params = HashMap<String, String>()
            params["mealset_id"] = mealsetId.toString()
            params["food_item_id"] = foodItemId.toString()
            params["addons_csv"] = addonsCsv
            params["address_lat"] = addressLat
            params["address_lon"] = addressLon
            params["full_address"] = fullAddress
            params["address_landmark"] = addressLandmark
            params["is_default_address"] = isDefaultAddress.toString()
            val response = api.placeOrder(params)
            response.body() to response.isSuccessful
        }
    }

    /**
     * Modify order
     *
     */
    @MainThread
    suspend fun modifyOrder(
        orderId: Int,
        foodItemId: Int,
        addonsCsv: String,
        addressLat: String,
        addressLon: String,
        fullAddress: String,
        addressLandmark: String,
        isDefaultAddress: Boolean
    ): Pair<GenericResponseBase<Any>?, Boolean> {
        return withContext(threadManager.io) {
            val params = HashMap<String, String>()
            params["order_id"] = orderId.toString()
            params["food_item_id"] = foodItemId.toString()
            params["addons_csv"] = addonsCsv
            params["address_lat"] = addressLat
            params["address_lon"] = addressLon
            params["full_address"] = fullAddress
            params["address_landmark"] = addressLandmark
            params["is_default_address"] = isDefaultAddress.toString()
            val response = api.modifyOrder(params)
            response.body() to response.isSuccessful
        }
    }

    /**
     * Rate order
     *
     */
    @MainThread
    suspend fun rateOrder(
        orderId: Int,
        rating: Int
    ): Pair<GenericResponseBase<Any>?, Boolean> {
        return withContext(threadManager.io) {
            val params = HashMap<String, String>()
            params["order_id"] = orderId.toString()
            params["rating"] = rating.toString()
            val response = api.rateOrder(params)
            response.body() to response.isSuccessful
        }
    }

    /**
     * Get order details
     *
     */
    @MainThread
    suspend fun getOrderDetails(
        orderId: Int
    ): Pair<GenericResponseBase<Order>?, Boolean> {
        return withContext(threadManager.io) {
            val params = HashMap<String, String>()
            params["order_id"] = orderId.toString()
            val response = api.getOrder(params)
            response.body() to response.isSuccessful
        }
    }

    /**
     * Fetch list of all orders in history
     *
     */
    @MainThread
    suspend fun listOrdersHistory(): Pair<GenericResponseBase<List<Order>>?, Boolean> {
        return withContext(threadManager.io) {
            val response = api.listOrderHistory()
            response.body() to response.isSuccessful
        }
    }

    /**
     * Cancel order
     *
     */
    @MainThread
    suspend fun cancelOrder(
        orderId: Int
    ): Pair<GenericResponseBase<Any>?, Boolean> {
        return withContext(threadManager.io) {
            val params = HashMap<String, String>()
            params["order_id"] = orderId.toString()
            val response = api.cancelOrder(params)
            response.body() to response.isSuccessful
        }
    }

}
