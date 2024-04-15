package fittibackendapp.common.datasource.configuration

import fittibackendapp.common.dto.TokenDto
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
class AuditorAwareConfiguration {
    @Bean
    fun auditorAware(): AuditorAware<Long> {
        return AuditorAware<Long> {
            // 현재 인증 정보를 확인
            val authentication = SecurityContextHolder.getContext().authentication

            // 인증 정보가 존재하고, credentials가 TokenDto 타입인 경우에만 사용자 ID 반환
            if (authentication != null && authentication.credentials is TokenDto) {
                val tokenDto = authentication.credentials as TokenDto
                Optional.of(tokenDto.userId)
            }
            else {
                // 인증 정보가 없거나 TokenDto 타입이 아닌 경우
                // 여기서는 Optional.empty()를 반환하지만, 필요에 따라 기본값을 설정할 수도 있습니다.
                Optional.empty()
            }
        }
    }
}
