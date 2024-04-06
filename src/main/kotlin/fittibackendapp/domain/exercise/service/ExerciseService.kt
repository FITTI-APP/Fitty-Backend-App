package fittibackendapp.domain.exercise.service

import fittibackendapp.domain.exercise.repository.ExerciseRepository
import fittibackendapp.dto.ExerciseDto
import fittibackendapp.dto.mapstruct.ExerciseMapstruct
import org.springframework.stereotype.Service

@Service
class ExerciseService(
    private val exerciseRepository: ExerciseRepository,
    private val exerciseMapstruct: ExerciseMapstruct
) {

    fun listExercisesByExerciseKindId(
        exerciseKindId: Long
    ): List<ExerciseDto> {
        val exercises = exerciseRepository.findAllByExerciseKindId(exerciseKindId)
        return exerciseMapstruct.toDtos(exercises)
    }
}
