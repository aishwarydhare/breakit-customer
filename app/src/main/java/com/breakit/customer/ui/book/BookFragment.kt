package com.breakit.customer.ui.book

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
import com.breakit.customer.databinding.FragmentBookBinding
import com.breakit.customer.databinding.MealsetItemBinding
import com.breakit.customer.di.modules.viewmodel.ViewModelFactory
import com.breakit.customer.model.FoodItem
import com.breakit.customer.model.MealSet
import com.breakit.customer.utils.extensions.observe
import com.breakit.customer.utils.extensions.setDataBindingView
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val MODE = "mode"

class BookFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val bookViewModel: BookViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentBookBinding

    private var mode = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            mode = it.getInt(MODE, -1)
        }
        if (mode != Constants.MEAL_DETAIL_MODIFY_BOOKING_MODE) {
            Common.orderToModify = null
        }
        binding = setDataBindingView(R.layout.fragment_book, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initView()
        initObservers()
        loadDate()
    }

    private fun loadDate() {
        bookViewModel.getTodayMealsetFromRemote()
    }

    private fun initView() {
        if(mode == Constants.MEAL_DETAIL_MODIFY_BOOKING_MODE)
            binding.pageTitle = "Update order"
        else
            binding.pageTitle = "Book Your Next Meal"

        binding.viewmodel = bookViewModel
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObservers() {
        binding.viewmodel = bookViewModel

        bookViewModel.nextMealSet.observe(viewLifecycleOwner) {
            listOf(
                binding.mealsetItemOne,
                binding.mealsetItemTwo,
                binding.mealsetItemThree,
                binding.mealsetItemFour,
                binding.mealsetItemFive
            ).forEachIndexed { index, mealsetItemBinding ->
                val foodItem = bookViewModel.nextMealSet.value?.foodItems?.get(index)
                mealsetItemBinding.foodItem = foodItem
                setOnItemClickedListener(
                    mealsetItemBinding,
                    foodItem!!,
                    bookViewModel.nextMealSet.value!!
                )
            }
        }
        bookViewModel.error.observe(viewLifecycleOwner) {
            Common.handleCommonApiErrorResponse(it, activity as MainActivity)
            if (it != null) {
                when (it) {

                }
            }
        }
    }

    private fun setOnItemClickedListener(
        mealsetItem: MealsetItemBinding,
        foodItem: FoodItem,
        todaysMealSet: MealSet
    ) {
        mealsetItem.setOnItemClicked {
            (activity as MainActivity).showMealDetailFragment(
                foodItem,
                mode = Constants.MEAL_DETAIL_CHECKOUT_MODE,
                mealSet = todaysMealSet
            )
        }
    }

}