package fittibackendapp.domain.auth.controller.response

import java.io.Serializable

data class EmailVerificationResponse(
    val response: Boolean,
): Serializable
