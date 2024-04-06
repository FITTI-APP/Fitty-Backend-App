package fittibackendapp.domain.exercise.graphql.mutation.input

import java.time.LocalDateTime

data class ExerciseSessionRecordInput(
    val exerciseSessionRecordId: Long?,
    val memo: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val exerciseSaveTypeId: Long,
    val exerciseExerciseRecordInputs: List<ExerciseExerciseRecordInput>
)


