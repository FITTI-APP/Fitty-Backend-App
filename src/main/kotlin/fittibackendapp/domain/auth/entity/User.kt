package fittibackendapp.domain.auth.entity

import fittibackendapp.common.entitiybase.AuditLoggingEnumBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "user")
class User(
    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    var name: String,

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    var email: String,

    @Size(max = 255)
    @Column(name = "password")
    var password: String? = null,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    var role: Role,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "login_type_id", nullable = false)
    var loginType: LoginType

): AuditLoggingEnumBase() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null
}
