package com.breakit.customer.ui.checkout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.breakit.customer.Common
import com.breakit.customer.MainActivity
import com.breakit.customer.R
import com.breakit.customer.data.customer.CustomerFixture
import com.breakit.customer.databinding.FragmentCheckoutBinding
import com.breakit.customer.di.modules.viewmodel.ViewModelFactory
import com.breakit.customer.utils.extensions.isValid
import com.breakit.customer.utils.extensions.observe
import com.breakit.customer.utils.extensions.setDataBindingView
import com.breakit.customer.utils.extensions.showSuccessSnackbar
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class CheckoutFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val checkoutViewModel: CheckoutViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentCheckoutBinding

    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setDataBindingView(R.layout.fragment_checkout, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initObservers()
    }

    private fun initObservers() {
        checkoutViewModel.isOrderPlaced.observe(viewLifecycleOwner) {
            it?.let {
                if (it < 0) {
                    (activity as MainActivity).showSuccessSnackbar("Oops, something went wrong")
                } else if (it > 0) {
                    var successMsg = "Order Placed"
                    if (Common.orderToModify != null) {
                        var successMsg = "Order Updated"
                    }
                    (activity as MainActivity).showSuccessSnackbar("Order Updated")
                    Common.toRefresh = true
                    findNavController().popBackStack(R.id.navigation_home, false)
                }
            }
        }
        checkoutViewModel.error.observe(viewLifecycleOwner) {
            Common.handleCommonApiErrorResponse(it, activity as MainActivity)
            if (it != null) {
                when (it) {

                }
            }
        }
    }

    private fun initView() {
        binding.viewmodel = checkoutViewModel
        checkoutViewModel.init(Common.foodItem)

        binding.addressEt.setText(Common.customer.fullAddress)
        binding.placeOrderBtn.setOnClickListener {
            if (binding.addressEt.text.isBlank() || binding.landmarkEt.text.isBlank()) {
                Toast.makeText(
                    context,
                    "Address and Landmark fields are compulsory",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            Common.customer = CustomerFixture.create(
                Common.customer,
                fullAddress = binding.addressEt.text.toString(),
                addressLandmark = binding.landmarkEt.text.toString()
            )
            checkoutViewModel.placeOrder(
                Common.foodItem,
                Common.customer,
                Common.mealSet!!,
                Common.orderToModify
            )
            (activity as MainActivity).showSuccessSnackbar("Order Placed")
            Common.toRefresh = true
        }

        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }

        // place picker
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS,
                Place.Field.NAME
            )
        )
        autocompleteFragment.setCountries("IN")
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i(
                    "TAG", "Place: ${place.id} ${place.latLng?.latitude}" +
                            " ${place.latLng?.longitude} ${place.address}"
                )
                Common.customer = CustomerFixture.create(
                    Common.customer,
                    lat = place.latLng?.latitude.toString(),
                    lon = place.latLng?.longitude.toString(),
                    fullAddress = place.address,
                    addressLandmark = place.name
                )
                binding.addressEt.setText(place.address.toString())
                binding.landmarkEt.setText(place.name.toString())
                resetMapLocation()
            }

            override fun onError(p0: Status) {
                Log.i("TAG", "Error: ${p0.statusMessage}")
            }
        })

        // map
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        resetMapLocation()
    }

    private fun resetMapLocation() {
        var currentLocation = LatLng(22.7196, 75.8577)  // Mumbai
        map.let {
            if (Common.customer.lat.isValid() && Common.customer.lon.isValid()) {
                currentLocation =
                    LatLng(Common.customer.lat!!.toDouble(), Common.customer.lon!!.toDouble())
            }
            it.addMarker(MarkerOptions().position(currentLocation).title("Delivery Location"))
            it.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
            it.setMinZoomPreference(14.0f)
        }
    }

}