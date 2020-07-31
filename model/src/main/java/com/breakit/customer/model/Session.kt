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
data class Session(
    @Json(name = "auth_token")
    var authToken: String? = null
) : Parcelable
