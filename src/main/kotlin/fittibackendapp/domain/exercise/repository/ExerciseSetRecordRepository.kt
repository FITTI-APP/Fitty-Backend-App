package fittibackendapp.domain.exercise.repository

import fittibackendapp.domain.exercise.entity.ExerciseSetRecord
import org.springframework.data.jpa.repository.JpaRepository

interface ExerciseSetRecordRepository: JpaRepository<ExerciseSetRecord, Long> {

    fun findAllByExerciseExerciseRecordIdIn(exerciseExerciseRecordIds: List<Long>): List<ExerciseSetRecord>
    fun findAllByExerciseExerciseRecordId(exerciseExerciseRecordId: Long?): List<ExerciseSetRecord>
}
