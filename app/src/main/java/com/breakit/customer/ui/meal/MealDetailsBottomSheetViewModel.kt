package com.breakit.customer.ui.meal

import com.breakit.customer.data.customer.CustomerRepository
import com.breakit.customer.data.login.LoginRepository
import com.breakit.customer.ui.common.UiStateViewModel
import javax.inject.Inject

/**
 * ViewModel for login view
 */
class MealDetailsBottomSheetViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val customerRepository: CustomerRepository
) : UiStateViewModel()