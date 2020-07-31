package com.breakit.customer.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Model class for any API response
 */
@JsonClass(generateAdapter = true)
data class GenericResponseBase<T>(
    var ok: Boolean?,
    @Json(name = "data")
    var data: T? = null
)
