package com.breakit.customer.ui.book

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.breakit.customer.data.mealset.MealsetRepository
import com.breakit.customer.data.order.OrderRepository
import com.breakit.customer.model.MealSet
import com.breakit.customer.ui.common.UiStateViewModel
import com.breakit.customer.utils.UiStateManager.UiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel for login view
 */
class BookViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val mealsetRepository: MealsetRepository
) : UiStateViewModel() {

    val nextMealSet = MutableLiveData<MealSet>()

    fun getTodayMealsetFromRemote() {
        uiState = UiState.LOADING
        viewModelScope.launch {
            val (session, result) = try {
                mealsetRepository.getTodayMealset()
            } catch (exception: IOException) {
                uiState = UiState.ERROR
                null to false
            } catch (exception: HttpException) {
                uiState = UiState.ERROR
                null to false
            }
            if (result) session?.let {
                if (it.ok == true) {
                    nextMealSet.value = it.data
                    uiState = UiState.LOADED
                }
            }
        }
    }

}
