package com.breakit.customer.ui.meal

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.breakit.customer.Common
import com.breakit.customer.Constants
import com.breakit.customer.R
import com.breakit.customer.data.customer.CustomerRepository
import com.breakit.customer.data.login.LoginRepository
import com.breakit.customer.databinding.FragmentMealDetailsBottomSheetBinding
import com.breakit.customer.model.Addons
import com.breakit.customer.model.FoodItem
import com.breakit.customer.ui.book.BookFragmentDirections
import com.breakit.customer.ui.home.HomeFragmentDirections
import com.breakit.customer.utils.async.ThreadManager
import com.breakit.customer.utils.extensions.setDataBindingView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import org.json.JSONObject
import javax.inject.Inject

private const val MODE = "MODE"

class MealDetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private var mode: Int? = null
    var foodItem: FoodItem? = null

    @Inject
    lateinit var loginRepository: LoginRepository

    @Inject
    lateinit var customerRepository: CustomerRepository

    @Inject
    lateinit var threadManager: ThreadManager

    private lateinit var binding: FragmentMealDetailsBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mode = it.getInt(MODE)
        }
        foodItem = Common.foodItem
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setDataBindingView(R.layout.fragment_meal_details_bottom_sheet, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        binding.foodItem = foodItem

        val addOns: List<Addons>
        if (mode == Constants.MEAL_DETAIL_MODIFY_BOOKING_MODE) {
            binding.submitBtn.text = "Update Order"
            binding.submitBtn.setOnClickListener {
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationBook(mode!!)
                findNavController().navigate(action)
                dismiss()
            }
            addOns = Common.orderToModify!!.addons!!
        } else if (mode == Constants.MEAL_DETAIL_CHECKOUT_MODE) {
            binding.submitBtn.text = "Checkout"
            binding.submitBtn.setOnClickListener {
                val action = BookFragmentDirections.actionNavigationBookToNavigationCheckout(mode!!)
                findNavController().navigate(action)
                dismiss()
            }
            addOns = foodItem!!.recommendedAddons!!
        } else {
            addOns = Common.orderEnRoute!!.addons!!
            binding.submitBtn.visibility = View.GONE
        }

        with(binding.addOnsRv) {
            layoutManager = object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean = false
            }
            adapter = AddOnRvAdapter(mode!!, addOns) { indexClicked: Int ->
                handleAddOnItemClick(indexClicked)
                adapter!!.notifyItemChanged(indexClicked)
            }
        }
    }

    private fun handleAddOnItemClick(index: Int) {
        val addOn = foodItem?.recommendedAddons?.get(index)!!
        addOn.isSelected = !(addOn.isSelected)
    }

    companion object {
        /**
         * @param mode 1 if for checkout, else 2 for food preview only
         * @return A new instance of fragment
         */
        @JvmStatic
        fun newInstance(mode: Int) =
            MealDetailsBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putInt(MODE, mode)
                }
            }

        @JvmStatic
        @BindingAdapter("parseJsonToText")
        fun parseJsonStringToText(view: TextView, jsonString: String) {
            var result = ""
            val nutrition = JSONObject(jsonString)
            nutrition.keys().forEach { key ->
                result += key.toString().toUpperCase()
                result += "\t\t"
                result += nutrition.getString(key) + " gm"
                result += "\n"
            }
            view.text = result
        }
    }
}