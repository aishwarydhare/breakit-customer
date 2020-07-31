package com.breakit.customer.di.bindings

import androidx.lifecycle.ViewModel
import com.breakit.customer.MainViewModel
import com.breakit.customer.di.modules.viewmodel.ViewModelKey
import com.breakit.customer.ui.book.BookViewModel
import com.breakit.customer.ui.checkout.CheckoutViewModel
import com.breakit.customer.ui.home.HomeViewModel
import com.breakit.customer.ui.login.LoginBottomSheetViewModel
import com.breakit.customer.ui.meal.MealDetailsBottomSheetViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBindings {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginBottomSheetViewModel::class)
    abstract fun bindLoginViewModel(loginBottomSheetViewModel: LoginBottomSheetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MealDetailsBottomSheetViewModel::class)
    abstract fun bindMealDetailsBottomSheetViewModel(mealDetailsBottomSheetViewModel: MealDetailsBottomSheetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CheckoutViewModel::class)
    abstract fun bindCheckoutViewModel(checkoutViewModel: CheckoutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookViewModel::class)
    abstract fun bindBookViewModel(bookViewModel: BookViewModel): ViewModel

}
