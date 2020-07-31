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
class Addons(

    @Json(name = "id")
    var id: Int? = null,

    @Json(name = "restaurant")
    var restaurant: Restaurant? = null,

    @Json(name = "name")
    var name: String? = null,

    @Json(name = "description")
    var description: String? = null,

    @Json(name = "amount")
    var amount: Int? = null,

    var isSelected: Boolean = false

) : Parcelable
