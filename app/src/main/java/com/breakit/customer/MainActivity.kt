package com.breakit.customer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.breakit.customer.data.customer.CustomerRepository
import com.breakit.customer.data.login.LoginRepository
import com.breakit.customer.databinding.ActivityMainBinding
import com.breakit.customer.di.modules.viewmodel.ViewModelFactory
import com.breakit.customer.model.FoodItem
import com.breakit.customer.model.MealSet
import com.breakit.customer.notifications.NotificationChannels
import com.breakit.customer.ui.login.LoginBottomSheetFragment
import com.breakit.customer.ui.meal.MealDetailsBottomSheetFragment
import com.breakit.customer.utils.extensions.hasNotificationChannelSupport
import com.breakit.customer.utils.extensions.requestGestureUi
import com.breakit.customer.utils.extensions.setDataBindingView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Custom factory for viewmodel
     *
     * Custom factory provides app related dependencies
     */
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var customerRepository: CustomerRepository

    @Inject
    lateinit var loginRepository: LoginRepository

    private val mainViewModel: MainViewModel by viewModels { viewModelFactory }

    var fragment: LoginBottomSheetFragment? = null

    /**
     * onCreate()
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Common.isLoggedIn.value = loginRepository.isLoggedIn()

        binding = setDataBindingView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)

        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            onNavDestinationChanged(destination)
        }

        requestGestureUi()
        createNotificationChannels()

        initView()
        initObservers()

        //todo
//        checkPermissions()
    }

    private fun initView() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            mainViewModel.arePermissionsGranted.set(true)
            showLoginBottomSheetFragment()
        }

        binding.locationAllowTv.setOnClickListener {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun checkPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // permission already provided
                mainViewModel.arePermissionsGranted.set(true)
            }
            else -> {
                // You can directly ask for the permission.
                mainViewModel.arePermissionsGranted.set(false)
            }
        }
    }

    private fun createNotificationChannels() {
        if (hasNotificationChannelSupport()) {
            NotificationChannels.newInstance(application).createNotificationChannels()
        }
    }

    private fun initObservers() {
        binding.viewModel = mainViewModel
        Common.isLoggedIn.observeForever {
            it?.let {
                if (!it) {
                    showLoginBottomSheetFragment()
                } else {
                    showHomeFragment()
                }
            }
        }
    }

    fun showMealDetailFragment(
        foodItem: FoodItem,
        mode: Int,
        mealSet: MealSet? = null
    ) {
        Common.foodItem = foodItem
        Common.mealSet = mealSet
        val fragment = MealDetailsBottomSheetFragment.newInstance(mode)
        fragment.show(this.supportFragmentManager, "meal detail")
    }

    private fun showLoginBottomSheetFragment() {
        fragment = LoginBottomSheetFragment()
        fragment!!.show(this.supportFragmentManager, "dialog")
        fragment!!.isCancelable = false
    }

    private fun showHomeFragment() {
        fragment?.let {
            fragment?.dismiss()
        }
        (application as BreakItCustomerApplication).syncCustomerProfileData()
        findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_home)
    }

    private fun onNavDestinationChanged(destination: NavDestination) {
        // bottom nav is visible on all screen
        //todo: remove
        binding.navView.visibility = View.GONE
        binding.navDivider.visibility = View.GONE

        when (destination.id) {
            R.id.navigation_checkout,
            R.id.navigation_book,
            R.id.navigation_splash
            -> {
                // Hide bottom nav on screens which don't need it
                binding.navDivider.visibility = View.GONE
                binding.navView.visibility = View.GONE
            }
        }
    }

    /**
     * Handle up navigation with nav controller
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(AppBarConfiguration(navController.graph)) ||
                super.onSupportNavigateUp()
    }

}
