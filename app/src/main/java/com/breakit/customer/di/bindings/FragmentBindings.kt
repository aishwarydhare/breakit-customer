package com.breakit.customer.di.bindings

import com.breakit.customer.ui.book.BookFragment
import com.breakit.customer.ui.checkout.CheckoutFragment
import com.breakit.customer.ui.home.HomeFragment
import com.breakit.customer.ui.login.LoginBottomSheetFragment
import com.breakit.customer.ui.meal.MealDetailsBottomSheetFragment
import com.breakit.customer.ui.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindings {

    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginDialogFragment(): LoginBottomSheetFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMealDetailsBottomSheetFragment(): MealDetailsBottomSheetFragment

    @ContributesAndroidInjector
    abstract fun contributeCheckoutFragment(): CheckoutFragment

    @ContributesAndroidInjector
    abstract fun contributeBookFragment(): BookFragment

}
