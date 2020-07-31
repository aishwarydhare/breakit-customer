package com.breakit.customer.utils.extensions

import android.content.res.Resources

/**
 * Extension to convert Int value to pixels
 */
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
