package fittibackendapp.domain.auth.controller

import fittibackendapp.domain.auth.controller.response.EmailVerificationResponse
import fittibackendapp.domain.auth.service.EmailVerificationService
import fittibackendapp.exception.InvalidateEmailException
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailVerificationController(
    private val emailVerificationService: EmailVerificationService,
) {

    @Operation(
        summary = "Send Email for Verification",
        description = "Send Email for Verification (Random Code XXXX) 10분내로 인증해야 합니다.",
    )
    @PostMapping("/emails/verifications-request")
    fun sendMessage(
        @RequestParam
        toEmail: String,
    ) {
        val emailRegex = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"
        if (!toEmail.matches(emailRegex.toRegex())) {
            throw InvalidateEmailException()
        }

        emailVerificationService.sendMessage(toEmail)
    }

    @Operation(
        summary = "Email Verification",
        description = "이메일 인증을 위한 API입니다. 랜덤코드를 입력해주세요.",
    )
    @GetMapping("/emails/verifications")
    fun emailVerification(
        @RequestParam
        email: String,
        @RequestParam
        code: String,
    ): EmailVerificationResponse {
        val reponse = emailVerificationService.verifyEmail(
            email = email,
            code = code,
        )
        return EmailVerificationResponse(reponse)
    }
}
