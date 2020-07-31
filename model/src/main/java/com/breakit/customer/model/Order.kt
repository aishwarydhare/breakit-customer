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
data class Order(
    @Json(name = "id")
    var id: Int? = null,

    @Json(name = "amount")
    var amount: Int? = null,

    @Json(name = "delivery_date")
    var deliveryDate: String? = null,

    @Json(name = "status")
    var status: Int? = null,

    @Json(name = "customer")
    var customer: Customer? = null,

    @Json(name = "delivery_executive")
    var deliveryExecutive: DeliveryExecutive? = null,

    @Json(name = "locality")
    var locality: Locality? = null,

    @Json(name = "restaurant")
    var restaurant: Restaurant? = null,

    @Json(name = "food_item")
    var fooditem: FoodItem? = null,

    @Json(name = "addons")
    var addons: List<Addons>? = null,

    @Json(name = "fail_reason")
    var failReason: String? = null,

    @Json(name = "full_address")
    var fullAddress: String? = null,

    @Json(name = "address_landmark")
    var addressLandmark: String? = null,

    @Json(name = "address_lat")
    var addressLat: String? = null,

    @Json(name = "address_lon")
    var addressLon: String? = null,

    @Json(name = "customer_mobile")
    var customerMobile: String? = null,

    @Json(name = "mealset")
    var mealset: MealSet? = null,

    @Json(name = "eta")
    var eta: String? = null
) : Parcelable
