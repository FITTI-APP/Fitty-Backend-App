package fittibackendapp.domain.diet.entity

import fittibackendapp.common.entitiybase.AuditLoggingBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Instant

@Entity
@Table(name = "diet_record")
class DietRecord(
    @NotNull
    @Column(name = "user_id", nullable = false)
    var userId: Long,
    @NotNull
    @Column(name = "date_time", nullable = false)
    var dateTime: Instant,
    @Size(max = 255)
    @NotNull
    @Column(name = "memo", nullable = false)
    var memo: String
): AuditLoggingBase() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null
}
