package fittibackendapp.domain.exercise.entity

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
import jakarta.validation.constraints.Size
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime

@Entity
@Table(name = "exercise_session_record")
class ExerciseSessionRecord(
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,
    @NotNull
    @Column(name = "start_time", nullable = false)
    var startTime: LocalDateTime,
    @NotNull
    @Column(name = "end_time", nullable = false)
    var endTime: LocalDateTime,
    @Size(max = 255)
    @NotNull
    @Column(name = "memo", nullable = false)
    var memo: String,
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "exercise_save_type_id", nullable = false)
    var exerciseSaveType: ExerciseSaveType,
): AuditLoggingBase() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null
}
