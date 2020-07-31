package com.breakit.customer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.breakit.customer.Common
import com.breakit.customer.Constants
import com.breakit.customer.MainActivity
import com.breakit.customer.R
import com.breakit.customer.data.customer.CustomerRepository
import com.breakit.customer.databinding.FragmentHomeBinding
import com.breakit.customer.di.modules.viewmodel.ViewModelFactory
import com.breakit.customer.utils.extensions.observe
import com.breakit.customer.utils.extensions.setDataBindingView
import com.breakit.customer.utils.extensions.showErrorSnackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val homeViewModel: HomeViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var customerRepository: CustomerRepository

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setDataBindingView(R.layout.fragment_home, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initObservers()
        loadData()
    }

    private fun loadData() {
        homeViewModel.getTodayOrderFromRemote()
        homeViewModel.getNextBookingFromRemote()
        homeViewModel.getTodayMealsetFromRemote()
    }

    private fun initView() {
        binding.viewmodel = homeViewModel

        binding.viewBtn.setOnClickListener {
            homeViewModel.todaysOrder.value?.let {
                (activity as MainActivity).showMealDetailFragment(
                    foodItem = it.fooditem!!,
                    mode = Constants.MEAL_DETAIL_VIEW_MODE
                )
                Common.orderEnRoute = it
            }
        }

        binding.viewBookingBtn.setOnClickListener {
            homeViewModel.nextBooking.value?.let {
                (activity as MainActivity).showMealDetailFragment(
                    foodItem = it.fooditem!!,
                    mode = Constants.MEAL_DETAIL_MODIFY_BOOKING_MODE,
                    mealSet = it.mealset
                )
                Common.orderToModify = it
            }
        }

        binding.trackMealBtn.setOnClickListener {
            homeViewModel.todaysOrder.value?.let {
                Common.orderEnRoute = it
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationMap()
                findNavController().navigate(action)
            }
        }

        binding.placeOrderBtn.setOnClickListener {
            val action =
                HomeFragmentDirections.actionNavigationHomeToNavigationBook(Constants.MEAL_DETAIL_CHECKOUT_MODE)
            findNavController().navigate(action)
        }

        binding.pullToRefresh.setOnRefreshListener {
            loadData()
        }

        checkForUIUpdate()
    }

    private var todayOrderExists = false
    private var nextBookingExists = false
    private var nextMealsetExists = false

    private fun initObservers() {
        binding.viewmodel = homeViewModel
        homeViewModel.todaysOrder.observe(viewLifecycleOwner) {
            todayOrderExists = it != null
            checkForUIUpdate()
        }
        homeViewModel.nextBooking.observe(viewLifecycleOwner) {
            nextBookingExists = it != null
            checkForUIUpdate()
        }
        homeViewModel.nextMealSet.observe(viewLifecycleOwner) {
            nextMealsetExists = it != null
            checkForUIUpdate()
        }
        homeViewModel.error.observe(viewLifecycleOwner) {
            Common.handleCommonApiErrorResponse(it, activity as MainActivity)
            if (it != null) {
                when (it) {
                    -1, -11, -2, -22, -3, -33 -> (activity as MainActivity).showErrorSnackbar("something went wrong, $it")
                }
            }
        }
    }

    private fun checkForUIUpdate() {
        if (todayOrderExists) {
            binding.todayOrderCv.visibility = View.VISIBLE
        } else {
            binding.todayOrderCv.visibility = View.GONE
        }
        if (nextBookingExists) {
            binding.placeOrderCv.visibility = View.GONE
            binding.bookedOrderCv.visibility = View.VISIBLE
        } else if (nextMealsetExists) {
            binding.bookedOrderCv.visibility = View.GONE
            binding.placeOrderCv.visibility = View.VISIBLE
        } else {
            binding.bookedOrderCv.visibility = View.GONE
            binding.placeOrderCv.visibility = View.GONE
        }

        binding.pullToRefresh.isRefreshing = false

        if (!todayOrderExists && !nextBookingExists && !nextMealsetExists) {
            binding.placeOrderCv.visibility = View.GONE
            binding.todayOrderCv.visibility = View.GONE
            binding.bookedOrderCv.visibility = View.GONE
            binding.nodataVg.visibility = View.VISIBLE
        } else {
            binding.nodataVg.visibility = View.GONE
        }
    }

}