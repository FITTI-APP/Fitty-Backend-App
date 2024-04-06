package fittibackendapp.domain.diet.entity

import fittibackendapp.common.entitiybase.AuditLoggingBase
import fittibackendapp.domain.auth.entity.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "target_pcf_ratio")

class TargetPcfRatio(
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,
    @NotNull
    @Column(name = "protein_ratio", nullable = false)
    var proteinRatio: Double,
    @NotNull
    @Column(name = "carbohydrate_ratio", nullable = false)
    var carbohydrateRatio: Double,
    @NotNull
    @Column(name = "fat_ratio", nullable = false)
    var fatRatio: Double
): AuditLoggingBase() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null
}
