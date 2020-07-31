package com.breakit.customer.utils

import java.util.regex.Pattern

object TextValidation {
    private val EMAIL_REGEX = Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
        Pattern.CASE_INSENSITIVE
    )
    private val PHONE_REGEX = Pattern.compile("[0-9]{10}")
    private val OTP_REGEX = Pattern.compile("[0-9]{4}")

    fun isValidEmail(emailStr: String): Boolean {
        return EMAIL_REGEX.matcher(emailStr).find()
    }

    fun isValidPhone(phoneStr: String): Boolean {
        return PHONE_REGEX.matcher(phoneStr).find()
    }

    fun isValidOtp(otpStr: String): Boolean {
        return OTP_REGEX.matcher(otpStr).find()
    }
}