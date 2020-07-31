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
class Restaurant(

    @Json(name = "id")
    var id: Int? = null,

    @Json(name = "name")
    var name: String? = null,

    @Json(name = "rating")
    var rating: Int? = null,

    @Json(name = "orders_fulfilled")
    var ordersFulfilled: Int? = null

) : Parcelable
