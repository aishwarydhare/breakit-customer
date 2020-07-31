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
class Locality(

    @Json(name = "id")
    var id: Int? = null,

    @Json(name = "name")
    var name: String? = null,

    @Json(name = "lat")
    var lat: String? = null,

    @Json(name = "lon")
    var lon: String? = null

) : Parcelable
