package com.breakit.customer.data.customer

import com.breakit.customer.model.Customer

object CustomerFixture {

    fun create(
        customer: Customer,
        id: Int? = null,
        userId: Int? = null,
        name: String? = null,
        mobile: String? = null,
        preference: String? = null,
        fullAddress: String? = null,
        addressLandmark: String? = null,
        lat: String? = null,
        lon: String? = null
    ): Customer {
        return Customer(
            id = id ?: customer.id,
            userId = userId ?: customer.userId,
            name = name ?: customer.name,
            mobile = mobile ?: customer.mobile,
            preference = preference ?: customer.preference,
            fullAddress = fullAddress ?: fullAddress,
            addressLandmark = addressLandmark ?: customer.addressLandmark,
            lat = lat ?: customer.lat,
            lon = lon ?: customer.lon
        )

    }

}