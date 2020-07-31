package com.breakit.customer.ui.checkout

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.breakit.customer.data.order.OrderRepository
import com.breakit.customer.model.Customer
import com.breakit.customer.model.FoodItem
import com.breakit.customer.model.MealSet
import com.breakit.customer.model.Order
import com.breakit.customer.ui.common.UiStateViewModel
import com.breakit.customer.utils.UiStateManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel for login view
 */
class CheckoutViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : UiStateViewModel() {

    val isOrderPlaced = MutableLiveData(0)
    val totalAmount = ObservableField("")
    val addOnsTotal = ObservableField("")
    val foodItemName = ObservableField("")
    val foodItemAmount = ObservableField("")
    private var addOnsCsv = ""

    private fun _placeOrder(foodItem: FoodItem, customer: Customer, mealSet: MealSet) {
        uiState = UiStateManager.UiState.LOADING
        viewModelScope.launch {
            val (session, result) = try {
                orderRepository.placeOrder(
                    mealsetId = mealSet.id!!,
                    foodItemId = foodItem.id!!,
                    addonsCsv = addOnsCsv,
                    addressLat = customer.lat.toString(),
                    addressLon = customer.lon.toString(),
                    fullAddress = customer.fullAddress!!,
                    addressLandmark = customer.addressLandmark!!,
                    isDefaultAddress = true
                )
            } catch (exception: IOException) {
                uiState = UiStateManager.UiState.ERROR
                isOrderPlaced.value = -1
                null to false
            } catch (exception: HttpException) {
                uiState = UiStateManager.UiState.ERROR
                isOrderPlaced.value = -1
                null to false
            }
            if (result) session?.let {
                if (it.ok == true) {
                    uiState = UiStateManager.UiState.LOADED
                    isOrderPlaced.value = 1
                } else {
                    isOrderPlaced.value = -1
                }
            } else {
                isOrderPlaced.value = -1
            }
        }
    }

    private fun _modifyOrder(foodItem: FoodItem, customer: Customer, order: Order) {
        uiState = UiStateManager.UiState.LOADING
        viewModelScope.launch {
            val (session, result) = try {
                orderRepository.modifyOrder(
                    orderId = order.id!!,
                    foodItemId = foodItem.id!!,
                    addonsCsv = addOnsCsv,
                    addressLat = customer.lat.toString(),
                    addressLon = customer.lon.toString(),
                    fullAddress = customer.fullAddress!!,
                    addressLandmark = customer.addressLandmark!!,
                    isDefaultAddress = true
                )
            } catch (exception: IOException) {
                uiState = UiStateManager.UiState.ERROR
                isOrderPlaced.value = -1
                null to false
            } catch (exception: HttpException) {
                uiState = UiStateManager.UiState.ERROR
                isOrderPlaced.value = -1
                null to false
            }
            if (result) session?.let {
                if (it.ok == true) {
                    uiState = UiStateManager.UiState.LOADED
                    isOrderPlaced.value = 1
                } else {
                    isOrderPlaced.value = -1
                }
            } else {
                isOrderPlaced.value = -1
            }
        }
    }

    fun placeOrder(foodItem: FoodItem, customer: Customer, mealSet: MealSet, order: Order? = null) {
        if (order == null) {
            _placeOrder(foodItem, customer, mealSet)
        } else {
            _modifyOrder(foodItem, customer, order)
        }
    }

    fun init(foodItem: FoodItem) {
        var addOnsTotalAmount = 0
        foodItem.recommendedAddons?.forEach {
            if (it.isSelected) {
                addOnsCsv += it.id.toString() + ","
                addOnsTotalAmount += it.amount!!
            }
        }
        val total = foodItem.amount!! + addOnsTotalAmount
        totalAmount.set(total.toString())
        addOnsTotal.set(addOnsTotalAmount.toString())
        foodItemName.set(foodItem.name)
        foodItemAmount.set(foodItem.amount!!.toString())
    }
}
