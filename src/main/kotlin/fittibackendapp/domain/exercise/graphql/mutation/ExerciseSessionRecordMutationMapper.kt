package fittibackendapp.domain.exercise.graphql.mutation

import fittibackendapp.domain.exercise.facade.ExerciseRecordMutationFacade
import fittibackendapp.domain.exercise.graphql.mutation.input.ExerciseSessionRecordInput
import fittibackendapp.dto.ExerciseSessionRecordDto
import fittibackendapp.exception.InvalidExerciseSessionInputException
import fittibackendapp.security.component.ArgumentResolver
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExerciseSessionRecordMutationMapper(
    private val exerciseRecordMutationFacade: ExerciseRecordMutationFacade,
    private val argumentResolver: ArgumentResolver
) {
    @MutationMapping
    fun putExerciseSessionRecord(
        @Argument
        exerciseSessionRecordInput: ExerciseSessionRecordInput
    ): ExerciseSessionRecordDto {
        val userId = argumentResolver.getUserId()
        validateExerciseSessionInput(exerciseSessionRecordInput)

        val exerciseExerciseRecordInputs = exerciseSessionRecordInput.exerciseExerciseRecordInputs
        exerciseRecordMutationFacade.deleteExerciseExerciseRecords(
            exerciseSessionRecordId = exerciseSessionRecordInput.exerciseSessionRecordId,
            exerciseExerciseRecordInputs = exerciseExerciseRecordInputs,
        )
        return exerciseRecordMutationFacade.createOrUpdateExerciseSessionRecord(
            userId = userId,
            exerciseSessionRecordInput = exerciseSessionRecordInput,
        )
    }

    @MutationMapping
    fun deleteExerciseSessionRecord(
        @Argument
        exerciseSessionRecordId: Long
    ): Boolean {
        val userId = argumentResolver.getUserId()
        exerciseRecordMutationFacade.deleteExerciseSessionRecord(
            userId = userId,
            exerciseSessionRecordId = exerciseSessionRecordId,
        )
        return true
    }

    private fun validateExerciseSessionInput(
        exerciseSessionRecordInput: ExerciseSessionRecordInput
    ) {
        if (exerciseSessionRecordInput.exerciseSessionRecordId == null) {
            val exerciseExerciseRecordInputs = exerciseSessionRecordInput.exerciseExerciseRecordInputs
            val exerciseSetRecordInputs = exerciseExerciseRecordInputs.flatMap { it.exerciseSetRecordInputs }

            val exerciseExerciseRecordIds = exerciseExerciseRecordInputs.map { it.exerciseExerciseRecordId }
            val exerciseSetRecordIds = exerciseSetRecordInputs.map { it.exerciseSetRecordId }

            if (exerciseExerciseRecordIds.all { it == null } && exerciseSetRecordIds.all { it == null })
                throw InvalidExerciseSessionInputException()
        }
    }
}
