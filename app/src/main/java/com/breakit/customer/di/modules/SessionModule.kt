package com.breakit.customer.di.modules

import android.app.Application
import com.breakit.customer.data.login.LoginPrefs
import com.breakit.customer.network.RequestHelper
import dagger.Module
import dagger.Provides

@Module
abstract class SessionModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideRequestHelper(
            application: Application,
            loginPrefs: LoginPrefs
        ): RequestHelper =
            RequestHelper(
                apiAuthToken = loginPrefs.authToken()
            )
    }
}
