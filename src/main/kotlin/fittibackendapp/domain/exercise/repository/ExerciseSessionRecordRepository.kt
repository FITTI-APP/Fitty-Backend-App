package fittibackendapp.domain.exercise.repository

import fittibackendapp.domain.auth.entity.User
import fittibackendapp.domain.exercise.entity.ExerciseSessionRecord
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ExerciseSessionRecordRepository: JpaRepository<ExerciseSessionRecord, Long> {

    fun findAllByUserAndStartTimeBetween(
        user: User,
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): List<ExerciseSessionRecord>
}
