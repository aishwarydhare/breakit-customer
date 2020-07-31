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
class MealSet (

    @Json(name = "id")
    var id: Int? = null,

    @Json(name = "locality")
    var locality: Locality? = null,

    @Json(name = "delivery_date")
    var deliveryDate: String? = null,

    @Json(name = "food_items")
    var foodItems: List<FoodItem>? = null

) : Parcelable
