package com.breakit.customer

import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.breakit.customer.model.Customer
import com.breakit.customer.model.FoodItem
import com.breakit.customer.model.MealSet
import com.breakit.customer.model.Order
import com.breakit.customer.utils.extensions.showErrorSnackbar
import com.breakit.customer.utils.extensions.showWarningSnackbar

object Common {

    lateinit var foodItem: FoodItem
    lateinit var customer: Customer
    var orderToModify: Order? = null
    var orderEnRoute: Order? = null
    var mealSet: MealSet? = null
    var toRefresh: Boolean = false

    val isLoggedIn = MutableLiveData<Boolean>(false)

    fun handleCommonApiErrorResponse(it: Int?, activity: MainActivity) {
        if (it != null) {
            when (it) {
                -1000 -> activity.showWarningSnackbar("Please check your connection and try again")
                -404 -> activity.showWarningSnackbar("No Data Found")
                -500 -> activity.showErrorSnackbar("Something went wrong")
                -401 -> {
                    activity.showWarningSnackbar("Please sign in again to continue")
                    isLoggedIn.value = false
                    activity.findNavController(R.id.nav_host_fragment)
                        .popBackStack(R.id.navigation_splash, true)
                }
            }
        }
    }

}