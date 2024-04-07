package fittibackendapp.domain.body.entity

import fittibackendapp.common.entitiybase.AuditLoggingBase
import fittibackendapp.domain.auth.entity.User
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
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "user_detail")
class UserDetail(
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,
    @Column(name = "age")
    var age: Int?,
    @Column(name = "height")
    var height: Double?,
    @Column(name = "weight")
    var weight: Double?,
    @Column(name = "muscle_mass")
    var muscleMass: Double?,
    @Column(name = "body_fat")
    var bodyFat: Double?,
    @Column(name = "target_weight")
    var targetWeight: Double?,
    @Column(name = "target_muscle_mass")
    var targetMuscleMass: Double?
): AuditLoggingBase() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null
}
