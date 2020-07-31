package com.breakit.customer.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


/**
 * Extension to run block inside [Observer]
 */
fun <T> MutableLiveData<T>.observe(owner: LifecycleOwner, block: (T?) -> Unit): Unit =
    observe(owner, Observer { block(it) })
