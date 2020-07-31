package com.breakit.customer.utils.extensions

import com.google.android.material.textfield.TextInputEditText
import com.breakit.customer.utils.TextValidation.isValidEmail
import com.breakit.customer.utils.TextValidation.isValidPhone

fun TextInputEditText.isValidEmail(): Boolean {
    val enteredText = this.text
    enteredText?.let {
        return when {
            enteredText.isEmpty() -> {
                false
            }
            isValidEmail(enteredText.toString()) -> {
                true
            }
            else -> {
                false
            }
        }
    }
    return false
}

/**
 * Validates entered text is valid 10 digit phone number
 */
fun TextInputEditText.isValidPhone(): Boolean {
    val enteredText = this.text
    enteredText?.let {
        return when {
            enteredText.isEmpty() -> {
                false
            }
            isValidPhone(enteredText.toString()) -> {
                true
            }
            else -> {
                false
            }
        }
    }
    return false
}

/**
 * Validates entered password
 */
fun TextInputEditText.validatePassword(): Boolean {
    val enteredText = this.text
    enteredText?.let {
        return enteredText.isNotEmpty()
    }
    return false
}
