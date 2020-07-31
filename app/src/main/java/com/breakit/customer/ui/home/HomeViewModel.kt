package com.breakit.customer.ui.home

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.breakit.customer.Constants
import com.breakit.customer.data.mealset.MealsetRepository
import com.breakit.customer.data.order.OrderRepository
import com.breakit.customer.model.MealSet
import com.breakit.customer.model.Order
import com.breakit.customer.ui.common.UiStateViewModel
import com.breakit.customer.utils.UiStateManager.UiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel for home view
 */
class HomeViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val mealsetRepository: MealsetRepository
) : UiStateViewModel() {

    val todaysOrder = MutableLiveData<Order>()
    val nextBooking = MutableLiveData<Order>()
    val nextMealSet = MutableLiveData<MealSet>()

    var orderStatus = ObservableInt()
    var mealName = ObservableField("")
    var deliveryNote = ObservableField("")
    var cardTitle = ObservableField("")
    var mealImage = ObservableField("")
    var etaObservable = ObservableField("")

    fun getTodayOrderFromRemote() {
        uiState = UiState.LOADING
        viewModelScope.launch {
            val (response, result) = try {
                orderRepository.getTodaysOrder()
            } catch (exception: IOException) {
                nextMealSet.value = null
                uiState = UiState.ERROR
                null to false
            } catch (exception: HttpException) {
                nextMealSet.value = null
                uiState = UiState.ERROR
                null to false
            }
            if (result) response?.let {
                if (it.ok == true) {
                    todaysOrder.value = it.data
                    uiState = UiState.LOADED
                    updateTodayOrderView()
                } else {
                    error.value = -3
                }
            } else {
                error.value = -33
            }
        }
    }

    fun getNextBookingFromRemote() {
        uiState = UiState.LOADING
        viewModelScope.launch {
            val (response, result) = try {
                orderRepository.getNextBooking()
            } catch (exception: IOException) {
                nextMealSet.value = null
                uiState = UiState.ERROR
                null to false
            } catch (exception: HttpException) {
                nextMealSet.value = null
                uiState = UiState.ERROR
                null to false
            }
            if (result) response?.let {
                if (it.ok == true) {
                    nextBooking.value = it.data
                    uiState = UiState.LOADED
                } else {
                    error.value = -2
                }
            } else {
                error.value = -22
            }
        }
    }

    fun getTodayMealsetFromRemote() {
        uiState = UiState.LOADING
        viewModelScope.launch {
            val (response, result) = try {
                mealsetRepository.getTodayMealset()
            } catch (exception: IOException) {
                nextMealSet.value = null
                uiState = UiState.ERROR
                null to false
            } catch (exception: HttpException) {
                nextMealSet.value = null
                uiState = UiState.ERROR
                null to false
            }
            if (result) response?.let {
                if (it.ok == true) {
                    nextMealSet.value = it.data
                    uiState = UiState.LOADED
                } else {
                    error.value = -1
                }
            } else {
                error.value = -11
            }
        }
    }

    private fun updateTodayOrderView() {
        with(todaysOrder.value) {
            if (this != null) {
                mealName.set(fooditem?.name)
                cardTitle.set("Your Meal Today")
                etaObservable.set(eta)
                orderStatus.set(status!!)
                mealImage.set(fooditem!!.image!!)

                when (status) {
                    Constants.ORDER_STATUS_BOOKED -> deliveryNote.set("Being Prepared")
                    Constants.ORDER_STATUS_OUT_FOR_DELIVERY -> deliveryNote.set("On its way")
                    Constants.ORDER_STATUS_COMPLETED -> deliveryNote.set("Delivered")
                    Constants.ORDER_STATUS_CANCELLED -> deliveryNote.set("Cancelled")
                    Constants.ORDER_STATUS_FAILED -> deliveryNote.set("Failed to deliver")
                    null -> {
                        cardTitle.set("No Order For Today")
                    }
                }
            }
        }
    }

}
