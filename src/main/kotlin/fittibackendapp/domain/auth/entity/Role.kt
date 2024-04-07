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
@Table(name = "role")
class Role(

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
        const val ROLE_USER = "ROLE_USER"
        const val ROLE_ADMIN = "ROLE_ADMIN"
    }
}
