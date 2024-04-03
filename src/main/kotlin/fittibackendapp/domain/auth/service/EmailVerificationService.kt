package fittibackendapp.domain.auth.service

import fittibackendapp.configuration.EmailConfiguration
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
        message.text = "Hello, this is a test email"
        message.subject = "Test email"


        try {
            emailSender.send(message)
        } catch (
            e: RuntimeException
        ) {
            log.debug("Failed to send email to $email")
        }
    }
}

