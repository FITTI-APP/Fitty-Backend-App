package fittibackendapp.domain.auth.service

import fittibackendapp.configuration.EmailConfiguration
import org.apache.commons.lang3.RandomStringUtils
import org.reflections.Reflections.log
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailVerificationService(
    @Qualifier(EmailConfiguration.BeanName.MAIL_SENDER)
    private val emailSender: JavaMailSender,
) {
    fun sendMessage(email: String) {
        val message = SimpleMailMessage()
        message.setTo(email)

        var code = RandomStringUtils.randomNumeric(4)
        message.text = "$EMAIL_CONTENT\n인증번호: $code"
        message.subject = EMAIL_TITLE
        
        try {
            emailSender.send(message)
        } catch (
            e: RuntimeException
        ) {
            log.debug("Failed to send email to $email")
        }
    }

    companion object {
        const val EMAIL_TITLE = "Fitti-이메일 인증 요청"
        const val EMAIL_CONTENT = "Fitti에 가입해주셔서 감사합니다. 아래의 인증번호를 입력하여 이메일 인증을 완료해주세요."
    }
}

