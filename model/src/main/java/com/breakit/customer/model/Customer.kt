package com.breakit.customer.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * Model class for Customer detail response
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class Customer(
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "user_id")
    val userId: Int? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "mobile")
    val mobile: String? = null,

    @Json(name = "preference")
    val preference: String? = null,

    @Json(name = "full_address")
    val fullAddress: String? = null,

    @Json(name = "address_landmark")
    val addressLandmark: String? = null,

    @Json(name = "lat")
    val lat: String? = null,

    @Json(name = "lon")
    val lon: String? = null

) : Parcelable
