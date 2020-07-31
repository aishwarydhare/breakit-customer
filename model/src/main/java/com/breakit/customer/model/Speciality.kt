package com.breakit.customer.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


/**
 * Model class for Login response
 */
@JsonClass(generateAdapter = true)
@Parcelize
class Speciality(
    @Json(name = "id")
    var id: Int? = null,

    @Json(name = "type")
    var type: String? = null,

    @Json(name = "icon")
    var icon: String? = null
) : Parcelable
