package com.breakit.customer.ui.map

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.breakit.customer.Common
import com.breakit.customer.R
import com.breakit.customer.data.order.OrderRepository
import com.breakit.customer.databinding.FragmentMapBinding
import com.breakit.customer.utils.extensions.setDataBindingView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject


class MapFragment : Fragment() {

    @Inject
    lateinit var orderRepository: OrderRepository

    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setDataBindingView(R.layout.fragment_map, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        binding.deliveryExecutive = Common.orderEnRoute!!.deliveryExecutive!!

        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.callBtn.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("tel:" + Common.orderEnRoute!!.deliveryExecutive!!.mobile)
            startActivity(callIntent)
        }

        // map
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        val currentLocation =
            LatLng(
                Common.orderEnRoute!!.deliveryExecutive!!.lat!!.toDouble(),
                Common.orderEnRoute!!.deliveryExecutive!!.lon!!.toDouble()
            )
        googleMap.addMarker(MarkerOptions().position(currentLocation).title("Your Meal"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
        googleMap.setMinZoomPreference(15.0f)
    }
}