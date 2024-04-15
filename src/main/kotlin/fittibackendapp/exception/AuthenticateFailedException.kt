package fittibackendapp.common.exception

import com.auth0.jwt.exceptions.JWTVerificationException
import fittibackendapp.common.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class AuthenticateFailedException(
    e: JWTVerificationException
): ResponseStatusReasonException(
    statusCode = HttpStatus.UNAUTHORIZED,
    reasonName = "AUTHENTICATION_FAILED",
    reasonMessage = "잘못된 인증 정보입니다. $e",
)
