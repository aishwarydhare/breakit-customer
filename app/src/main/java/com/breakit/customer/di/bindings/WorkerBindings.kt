package com.breakit.customer.di.bindings

import com.breakit.customer.ui.settings.SignOutWorker
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WorkerBindings {

    @ContributesAndroidInjector
    abstract fun bindSignOutWorker(): SignOutWorker

}
