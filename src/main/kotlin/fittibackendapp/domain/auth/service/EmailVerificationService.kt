package fittibackendapp.domain.auth.service

import fittibackendapp.configuration.EmailConfiguration
import fittibackendapp.redis.service.RedisService
import org.apache.commons.lang3.RandomStringUtils
import org.reflections.Reflections.log
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Service
class EmailVerificationService(
    @Qualifier(EmailConfiguration.BeanName.MAIL_SENDER)
    private val emailSender: JavaMailSender,
    private val redisService: RedisService,
    @Qualifier(EmailConfiguration.BeanName.AUTH_CODE_EXPIRATION_MILLS)
    private val authCodeExpirationMills: Long,
) {
    @Transactional
    fun sendMessage(email: String) {
        val message = SimpleMailMessage()
        message.setTo(email)

        var code = RandomStringUtils.randomNumeric(4)
        message.text = "$EMAIL_CONTENT\n인증번호: $code"
        message.subject = EMAIL_TITLE
        redisService.deleteByKey(AUTH_CODE_PREFIX + email)
        redisService.setValueWithExpire(AUTH_CODE_PREFIX + email, code, Duration.ofMillis(authCodeExpirationMills))
        try {
            emailSender.send(message)
        } catch (
            e: RuntimeException
        ) {
            log.debug("Failed to send email to $email")
        }
    }

    @Transactional
    fun verifyEmail(email: String, code: String): Boolean {
        val savedCode = redisService.getByKey(AUTH_CODE_PREFIX + email)
        redisService.deleteByKey(AUTH_CODE_PREFIX + email)
        return savedCode == code
    }

    companion object {
        const val EMAIL_TITLE = "Fitti-이메일 인증 요청"
        const val EMAIL_CONTENT = "Fitti에 가입해주셔서 감사합니다. 아래의 인증번호를 입력하여 이메일 인증을 완료해주세요."
        const val AUTH_CODE_PREFIX = "auth-code"
    }
}

