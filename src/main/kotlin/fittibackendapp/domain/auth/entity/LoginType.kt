package fittibackendapp.domain.auth.entity

import fittibackendapp.common.entitiybase.AuditLoggingEnumBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "login_type")
class LoginType(
    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    var name: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0
): AuditLoggingEnumBase() {
    companion object {
        const val EMAIL = "EMAIL"
        const val GOOGLE = "GOOGLE"
        const val KAKAO = "KAKAO"
        const val NAVER = "NAVER"
        const val FACEBOOK = "FACEBOOK"
    }
}
