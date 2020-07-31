package com.breakit.customer.ui.meal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.RecyclerView
import com.breakit.customer.Constants
import com.breakit.customer.R
import com.breakit.customer.databinding.AddonItemBinding
import com.breakit.customer.model.Addons

class AddOnRvAdapter(
    private val mode: Int,
    private val addOns: List<Addons>,
    private val handleOnAddOnItemClick: (Int) -> Unit
) : RecyclerView.Adapter<AddOnRvAdapter.AddOnItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddOnItemViewHolder {
        return AddOnItemViewHolder.create(parent, R.layout.addon_item)
    }

    override fun getItemCount(): Int {
        return addOns.size
    }

    override fun onBindViewHolder(holder: AddOnItemViewHolder, position: Int) {
        val addOn = addOns[position]
        holder.bindTo(addOn = addOn, mode = mode) { clickPosition ->
            handleOnAddOnItemClick(clickPosition)
        }
    }

    class AddOnItemViewHolder(private val binding: AddonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(addOn: Addons, mode: Int, onItemClick: (Int) -> Unit) {
            binding.root.visibility = View.VISIBLE
            binding.addOn = addOn
            if (mode == Constants.MEAL_DETAIL_VIEW_MODE || mode == Constants.MEAL_DETAIL_MODIFY_BOOKING_MODE) {
                addOn.isSelected = true
            } else {
                binding.root.setOnClickListener {
                    onItemClick(adapterPosition)
                }
            }
            binding.executePendingBindings()
        }

        companion object {
            /**
             * Factory function to create [AddOnItemViewHolder]
             */
            fun create(parent: ViewGroup, layoutId: Int): AddOnItemViewHolder =
                AddOnItemViewHolder(
                    inflate(
                        LayoutInflater.from(parent.context),
                        layoutId, parent, false
                    )
                )

            @JvmStatic
            @BindingAdapter("isAddOnSelected")
            fun isAddOnSelected(view: ImageView, isSelected: Boolean) {
                when (isSelected) {
                    true -> view.setImageResource(R.drawable.ic_baseline_check_box_24_active)
                    else -> view.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24)
                }
            }
        }
    }
}
