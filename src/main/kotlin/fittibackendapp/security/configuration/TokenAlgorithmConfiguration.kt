package fittibackendapp.security.configuration

import com.auth0.jwt.algorithms.Algorithm
import fittibackendapp.common.configuration.ApplicationConfiguration
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TokenAlgorithmConfiguration(
    @Qualifier(ApplicationConfiguration.BeanName.PROFILE)
    private val profile: String
) {
    @Bean
    fun tokenAlgorithm(): Algorithm {
        // val secret = secretsManagerService.getJwtSecretKey() todo
        val time = System.currentTimeMillis()
        val secret = "fitti_secret_key_$profile"
        return Algorithm.HMAC256(secret)
    }
}
