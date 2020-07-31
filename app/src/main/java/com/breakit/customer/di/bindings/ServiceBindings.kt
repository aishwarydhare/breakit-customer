package com.breakit.customer.di.bindings

import com.google.firebase.messaging.FirebaseMessagingService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBindings {

    @ContributesAndroidInjector
    abstract fun contributeFirebaseService(): FirebaseMessagingService

}