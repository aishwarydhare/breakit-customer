package com.breakit.customer.utils.extensions


/**
 * File will contain all the extension functions for [String]s
 */

/**
 * Check string is not null and empty
 *
 * @param {String} text
 * @return {Boolean}
 */
fun String?.isValid(): Boolean {
    return this != null && this.isNotBlank()
}
