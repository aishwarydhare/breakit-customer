package com.breakit.customer.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * Model class for orders
 */
@JsonClass(generateAdapter = true)
@Parcelize
class DeliveryExecutive (

    @Json(name = "id")
    var id: Int? = null,

    @Json(name = "user_id")
    var userId: Int? = null,

    @Json(name = "mobile")
    var mobile: String? = null,

    @Json(name = "name")
    var name: String? = null,

    @Json(name = "rating")
    var rating: Int? = null,

    @Json(name = "orders_fulfilled")
    var ordersFulfilled: Int? = null,

    @Json(name = "lat")
    var lat: String? = null,

    @Json(name = "lon")
    var lon: String? = null

) : Parcelable
