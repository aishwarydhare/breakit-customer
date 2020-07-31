package com.breakit.customer.di

import com.breakit.customer.MainActivity
import com.breakit.customer.di.bindings.FragmentBindings
import com.breakit.customer.di.bindings.ServiceBindings
import com.breakit.customer.di.bindings.ViewModelBindings
import com.breakit.customer.network.AuthInterceptor
import com.breakit.customer.network.AuthInterceptorImpl
import com.breakit.customer.utils.Logger
import com.breakit.customer.utils.LoggerImpl
import com.breakit.customer.utils.async.ThreadManager
import com.breakit.customer.utils.async.ThreadManagerImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBindings {

    @ContributesAndroidInjector(
        modules = [
            FragmentBindings::class,
            ViewModelBindings::class,
            ServiceBindings::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity

    @Binds
    abstract fun provideThreadManager(threadManager: ThreadManagerImpl): ThreadManager

    @Binds
    abstract fun provideAuthInterceptor(authInterceptor: AuthInterceptorImpl): AuthInterceptor

    @Binds
    abstract fun provideLogger(logger: LoggerImpl): Logger
}

