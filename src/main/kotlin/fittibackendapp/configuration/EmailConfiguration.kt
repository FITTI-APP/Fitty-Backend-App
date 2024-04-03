package fittibackendapp.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.Properties

@Configuration
class EmailConfiguration(
    private val enviroment: Environment
) {

    @Bean(BeanName.MAIL_SENDER)
    fun JavaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()

        mailSender.host = enviroment.getProperty("spring.email.host")
        mailSender.port = enviroment.getProperty("spring.email.port")!!.toInt()
        mailSender.username = enviroment.getProperty("spring.email.username")
        mailSender.password = enviroment.getProperty("spring.email.password")
        mailSender.defaultEncoding = "UTF-8"
        mailSender.javaMailProperties = getProperties()
        return mailSender
    }

    private fun getProperties(): Properties {
        val properties: Properties = Properties().also {
            it.put("mail.smtp.auth", enviroment.getProperty("spring.email.properties.smtp.auth"))
            it.put(
                "mail.smtp.starttls.enable",
                enviroment.getProperty("spring.email.properties.smtp.starttls.enable"),
            )
            it.put(
                "mail.smtp.starttls.required",
                enviroment.getProperty("spring.email.properties.smtp.starttls.required"),
            )
            it.put(
                "mail.smtp.connectiontimeout",
                enviroment.getProperty("spring.email.properties.smtp.connectiontimeout"),
            )
            it.put("mail.smtp.timeout", enviroment.getProperty("spring.email.properties.smtp.timeout"))
            it.put("mail.smtp.writetimeout", enviroment.getProperty("spring.email.properties.smtp.writetimeout"))
        }
        return properties
    }

    object BeanName {
        const val MAIL_SENDER = "mailSender"
    }
}


