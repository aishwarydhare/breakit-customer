package com.breakit.customer.ui.common

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.breakit.customer.utils.UiStateManager.UiState

/**
 * Interface to be implemented by ViewModel class if it makes network requests or updates UI
 */
open class UiStateViewModel : ViewModel() {

    var error = MutableLiveData(0)

    /**
     * [UiState]
     */
    var _uiState = UiState.LOADED

    var uiState: UiState
        get() {
            return _uiState
        }
        set(value) {
            when (value) {
                UiState.INIT -> {
                    isLoading.set(true)
                }
                UiState.LOADING -> {
                    isLoading.set(true)
                }
                UiState.INIT_EMPTY -> {
                    error.value = 404
                    hasNoData.set(true)
                }
                UiState.EMPTY -> {
                    error.value = 404
                    hasNoData.set(true)
                }
                UiState.ERROR_CONNECTION -> {
                    error.value = 400
                    hasNetworkError.set(true)
                }
                UiState.ERROR -> {
                    error.value = 500
                    hasError.set(true)
                }
                else -> {
                    error.value = 0
                    isLoading.set(false)
                    hasNoData.set(false)
                    hasNetworkError.set(false)
                    hasError.set(false)
                }
            }
            _uiState = value
        }

    /**
     * helper to return all cases when the view is in loading state
     */
    val isLoading = ObservableBoolean(false)

    /**
     * helper to return all cases when view is not having any data
     */
    val hasNoData = ObservableBoolean(false)

    /**
     * helper to return all cases when view could not be loaded due to network error
     */
    val hasNetworkError = ObservableBoolean(false)

    /**
     * helper to return all cases when view could not be loaded due to unknown error
     */
    val hasError = ObservableBoolean(false)
}
