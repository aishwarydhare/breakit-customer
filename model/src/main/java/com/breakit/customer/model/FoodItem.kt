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
class FoodItem(
    @Json(name = "id")
    var id: Int? = null,

    @Json(name = "restaurant")
    var restaurant: Restaurant? = null,

    @Json(name = "name")
    var name: String? = null,

    @Json(name = "description")
    var description: String? = null,

    @Json(name = "speciality")
    var speciality: Speciality? = null,

    @Json(name = "nutrition")
    var nutrition: String? = null,

    @Json(name = "amount")
    var amount: Int? = null,

    @Json(name = "recommended_addons")
    var recommendedAddons: List<Addons>? = null,

    @Json(name = "image")
    var image: String? = null

) : Parcelable