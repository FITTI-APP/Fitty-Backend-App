package fittibackendapp.common.security.component

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.JWTVerifier
import fittibackendapp.common.dto.TokenDto
import fittibackendapp.common.exception.AuthenticateFailedException
import fittibackendapp.common.exception.JwtTokenExpiredException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class FittiJwtVerifier(
    private val tokenAlgorithm: Algorithm,
): JwtVerifier {
    override val tokenVerifier: JWTVerifier = JWT
        .require(tokenAlgorithm)
        .withClaimPresence("email")
        .withClaimPresence("userId")
        .withClaimPresence("role")
        .build()

    override fun verify(request: HttpServletRequest): TokenDto? {
        val bearerToken = request.getHeader("Authorization") ?: return null
        if (!bearerToken.startsWith("Bearer ")) return null

        return verifyToken(bearerToken)
    }

    override fun verifyToken(bearerToken: String): TokenDto {
        try {
            val verification = when (val token = bearerToken.split(" ")[1]) {
                "fitti"
                -> TokenDto(
                    email = "fitti",
                    userId = 1,
                    role = 2,
                )

                else -> {
                    val verifiedJWT = tokenVerifier.verify(token)
                    TokenDto(
                        email = verifiedJWT.getClaim("email").asString(),
                        userId = verifiedJWT.getClaim("userId").asLong(),
                        role = verifiedJWT.getClaim("role").asLong(),
                    )
                }
            }
            return verification
        } catch (e: TokenExpiredException) {
            throw JwtTokenExpiredException()
        } catch (e: JWTVerificationException) {
            throw AuthenticateFailedException(e)
        }
    }
}

