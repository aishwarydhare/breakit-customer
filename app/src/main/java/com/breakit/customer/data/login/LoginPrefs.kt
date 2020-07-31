package com.breakit.customer.data.login

import com.breakit.customer.data.prefs.PrefUtils
import com.breakit.customer.model.Session
import javax.inject.Inject

/**
 * Prefs helper for user account session
 *
 * Data related to user account session preferences should be stored and accessed using [LoginPrefs]
 */
open class LoginPrefs @Inject constructor(private val prefs: PrefUtils) {

    companion object {
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val USER_AUTH_TOKEN = "user_auth_token"
    }

    init {
        prefs.init("accounts")
    }

    /**
     * Store the user session to preferences
     *
     * @param [session] [Session] Session details
     */
    fun storeUser(session: Session) {
        with(prefs) {
            set(USER_AUTH_TOKEN, session.authToken)
            set(IS_LOGGED_IN, true)
            commit()
        }
    }

    /**
     * Clear preferences
     */
    fun clear(): Boolean = prefs.clear()

    /**
     * Check if user is logged in
     *
     * @return True if user is logged in, otherwise False
     */
    fun isLoggedIn(): Boolean = prefs.get(IS_LOGGED_IN, false)

    /**
     * Get the auth token for logged in user
     *
     * @return authToken if the user is logged in or empty string
     */
    fun authToken(): String = prefs.get(USER_AUTH_TOKEN, "")

}
