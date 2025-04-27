package ge.fitness.auth.presentation.utils

import ge.fitness.auth.presentation.R
import ge.fitness.core.domain.auth.AuthError

fun AuthError.toStringRes(): Int {
    return when (this) {
        AuthError.LoginError.INVALID_EMAIL -> {
            R.string.unknown
        }

        AuthError.LoginError.USER_DOESNT_EXIST -> {
            R.string.user_doesn_t_exist
        }

        AuthError.LoginError.INVALID_PASSWORD -> {
            R.string.invalid_password
        }

        AuthError.LoginError.UNKNOWN -> {
            R.string.unknown
        }

        AuthError.RegisterError.USER_ALREADY_EXISTS -> {
            R.string.user_already_exists
        }

        AuthError.RegisterError.INVALID_EMAIL -> {
            R.string.invalid_email
        }

        AuthError.RegisterError.INVALID_PASSWORD -> {
            R.string.invalid_password
        }

        AuthError.RegisterError.UNKNOWN -> {
            R.string.unknown
        }
    }
}