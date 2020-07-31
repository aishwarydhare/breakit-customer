package com.breakit.customer

import android.app.Application
import android.util.Log
import com.breakit.customer.data.customer.CustomerRepository
import com.breakit.customer.data.login.LoginRepository
import com.breakit.customer.di.DaggerAppComponent
import com.breakit.customer.utils.async.ThreadManager
import com.google.android.libraries.places.api.Places
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject


private const val TAG = "APP"

class BreakItCustomerApplication : Application(), HasAndroidInjector {

    /**
     * Injector for Android components
     */
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    /**
     * Delegate class to handle app lifecycle events
     */
    @Inject
    lateinit var appLifeCycleDelegate: AppLifeCycleDelegate

    @Inject
    lateinit var threadManager: ThreadManager

    @Inject
    lateinit var customerRepository: CustomerRepository

    @Inject
    lateinit var loginRepository: LoginRepository

    /**
     * See [Application.onCreate]
     */
    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().app(this)
            .build()
            .inject(this)
        if (!Places.isInitialized()) {
            Places.initialize(
                applicationContext,
                getString(R.string.google_maps_key),
                Locale.US
            );
        }
        FirebaseApp.initializeApp(this)
        getFirebaseToken()
    }

    fun syncCustomerProfileData() {
        CoroutineScope(Dispatchers.IO).launch {
            val (genericResponse, result) = try {
                customerRepository.getCustomerFromRemote()
            } catch (exception: IOException) {
                null to false
            } catch (exception: HttpException) {
                null to false
            }
            if (result) genericResponse?.data!!.let {
                Common.customer = it
            }
        }
    }

    fun updateFcmTokenOnRemote(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val (genericResponse, result) = try {
                loginRepository.fcmUpdateOnRemote(token)
            } catch (exception: IOException) {
                null to false
            } catch (exception: HttpException) {
                null to false
            }
            if (result) genericResponse?.ok?.let {
                Log.d(TAG, "save to remote token $it")
            }
        }
    }

    fun getFirebaseToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
            val newToken: String = instanceIdResult.getToken()
            Log.e("newToken", newToken)
            updateFcmTokenOnRemote(newToken)
        }
    }

    /**
     * @return injector for Android components
     */
    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}
