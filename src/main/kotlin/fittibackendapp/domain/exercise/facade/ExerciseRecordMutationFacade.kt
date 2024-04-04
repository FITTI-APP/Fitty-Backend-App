package fittibackendapp.domain.exercise.facade

import fittibackendapp.domain.exercise.graphql.mutation.input.ExerciseExerciseRecordInput
import fittibackendapp.domain.exercise.graphql.mutation.input.ExerciseSessionRecordInput
import fittibackendapp.domain.exercise.graphql.mutation.input.ExerciseSetRecordInput
import fittibackendapp.domain.exercise.service.ExerciseExerciseRecordService
import fittibackendapp.domain.exercise.service.ExerciseSessionRecordService
import fittibackendapp.domain.exercise.service.ExerciseSetRecordService
import fittibackendapp.dto.ExerciseExerciseRecordDto
import fittibackendapp.dto.ExerciseSessionRecordDto
import fittibackendapp.dto.ExerciseSetRecordDto
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ExerciseRecordMutationFacade(
    private val exerciseSessionRecordService: ExerciseSessionRecordService,
    private val exerciseExerciseRecordService: ExerciseExerciseRecordService,
    private val exerciseSetRecordService: ExerciseSetRecordService,
) {

    @Transactional
    fun createOrUpdateExerciseSessionRecord(
        userId: Long,
        exerciseSessionRecordInput: ExerciseSessionRecordInput
    ): ExerciseSessionRecordDto {
        if (exerciseSessionRecordInput.exerciseSessionRecordId == null) {
            exerciseSessionRecordService.create(
                userId = userId,
                memo = exerciseSessionRecordInput.memo,
                startTime = exerciseSessionRecordInput.startTime,
                endTime = exerciseSessionRecordInput.endTime,
                saveTypeId = exerciseSessionRecordInput.saveTypeId,
            )
        }
        else {
            exerciseSessionRecordService.update(
                userId = userId,
                exerciseSessionRecordId = exerciseSessionRecordInput.exerciseSessionRecordId,
                memo = exerciseSessionRecordInput.memo,
                startTime = exerciseSessionRecordInput.startTime,
                endTime = exerciseSessionRecordInput.endTime,
                saveTypeId = exerciseSessionRecordInput.saveTypeId,
            )
        }.run {
            createOrUpdateExerciseExerciseRecords(
                userId = userId,
                exerciseSessionRecordId = this.id,
                exerciseExerciseRecordInputs = exerciseSessionRecordInput.exerciseExerciseRecordInputs,
            )
            return this
        }
    }

    @Transactional
    fun createOrUpdateExerciseExerciseRecords(
        userId: Long,
        exerciseSessionRecordId: Long,
        exerciseExerciseRecordInputs: List<ExerciseExerciseRecordInput>
    ): List<ExerciseExerciseRecordDto> {
        return exerciseExerciseRecordInputs.map {
            if (it.exerciseExerciseRecordId == null) {
                exerciseExerciseRecordService.create(
                    exerciseSessionRecordId = exerciseSessionRecordId,
                    exerciseId = it.exerciseId,
                    order = it.order,
                    memo = it.memo,
                )
            }
            else {
                exerciseExerciseRecordService.update(
                    exerciseSessionRecordId = exerciseSessionRecordId,
                    exerciseExerciseRecordId = it.exerciseExerciseRecordId,
                    exerciseId = it.exerciseId,
                    order = it.order,
                    memo = it.memo,
                )
            }.apply {
                createOrUpdateExerciseSetRecords(
                    userId = userId,
                    exerciseExerciseRecordId = this.id,
                    exerciseSetRecordInputs = it.exerciseSetRecordInputs,
                )
            }
        }
    }

    @Transactional
    fun createOrUpdateExerciseSetRecords(
        userId: Long,
        exerciseExerciseRecordId: Long,
        exerciseSetRecordInputs: List<ExerciseSetRecordInput>
    ): List<ExerciseSetRecordDto> {
        return exerciseSetRecordInputs.map {
            if (it.exerciseSetRecordId == null) {
                exerciseSetRecordService.create(
                    exerciseExerciseRecordId = exerciseExerciseRecordId,
                    weight = it.weight,
                    reps = it.reps,
                    order = it.order,
                    memo = it.memo,
                    distance = it.distance,
                    totalTime = it.totalTime,
                )
            }
            else {
                exerciseSetRecordService.update(
                    exerciseExerciseRecordId = exerciseExerciseRecordId,
                    exerciseSetRecordId = it.exerciseSetRecordId,
                    weight = it.weight,
                    order = it.order,
                    memo = it.memo,
                    reps = it.reps,
                    distance = it.distance,
                    totalTime = it.totalTime,
                )
            }
        }
    }

    @Transactional
    fun deleteExerciseSessionRecord(
        userId: Long,
        exerciseSessionRecordId: Long
    ) {
        val exerciseExerciseRecordIds = exerciseExerciseRecordService
            .listExerciseExerciseRecordsByExerciseSessionRecordId(exerciseSessionRecordId)
            .map { it.id }

        val exerciseSetRecords = exerciseSetRecordService
            .listByExerciseExerciseRecordIds(exerciseExerciseRecordIds)
            .map { it.id }

        exerciseSetRecordService.deleteAll(exerciseSetRecords)
        exerciseExerciseRecordService.deleteAll(exerciseExerciseRecordIds)
        exerciseSessionRecordService.delete(
            exerciseSessionRecordId = exerciseSessionRecordId,
        ) // 이렇게 안해도 Cascade로 인해 자동으로 삭제됨. 하지만 확실하게 하기위해 삭제
    }

    @Transactional
    fun deleteExerciseExerciseRecords(
        exerciseSessionRecordId: Long?,
        exerciseExerciseRecordInputs: List<ExerciseExerciseRecordInput>
    ) {
        exerciseExerciseRecordService
            .listExerciseExerciseRecordsByExerciseSessionRecordId(exerciseSessionRecordId)
            .ifEmpty { null }
            ?.run {
                map { it.id }
                    .filter {
                        it !in exerciseExerciseRecordInputs
                            .map { inputs -> inputs.exerciseExerciseRecordId }
                    }
                    .run { exerciseExerciseRecordService.deleteAll(this) }
            }

        exerciseExerciseRecordInputs.map {
            deleteExerciseSetRecords(
                exerciseExerciseRecordId = it.exerciseExerciseRecordId,
                exerciseSetRecordInputs = it.exerciseSetRecordInputs,
            )
        }
    }

    @Transactional
    fun deleteExerciseSetRecords(
        exerciseExerciseRecordId: Long?,
        exerciseSetRecordInputs: List<ExerciseSetRecordInput>
    ) {
        exerciseSetRecordService
            .listExerciseSetRecordsByExerciseExerciseRecordId(exerciseExerciseRecordId)
            .ifEmpty { null }
            ?.run {
                map { it.id }
                    .filter {
                        it !in exerciseSetRecordInputs
                            .map { inputs -> inputs.exerciseSetRecordId }
                    }
                    .run { exerciseSetRecordService.deleteAll(this) }
            }
    }
}
