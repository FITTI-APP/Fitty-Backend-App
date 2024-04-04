package fittibackendapp.domain.exercise.service

import fittibackendapp.domain.auth.repository.UserRepository
import fittibackendapp.domain.exercise.entity.ExerciseSessionRecord
import fittibackendapp.domain.exercise.repository.ExerciseSaveTypeRepository
import fittibackendapp.domain.exercise.repository.ExerciseSessionRecordRepository
import fittibackendapp.dto.ExerciseSessionRecordDto
import fittibackendapp.dto.mapstruct.ExerciseSessionRecordMapStruct
import fittibackendapp.exception.NotFoundExerciseSaveTypeException
import fittibackendapp.exception.NotFoundExerciseSessionRecordException
import fittibackendapp.exception.NotFoundUserException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class ExerciseSessionRecordService(
    private val exerciseSessionRecordRepository: ExerciseSessionRecordRepository,
    private val userRepository: UserRepository,
    private val exerciseSessionRecordMapStruct: ExerciseSessionRecordMapStruct,
    private val exerciseSaveTypeRepository: ExerciseSaveTypeRepository,
) {

    @Transactional
    fun delete(
        exerciseSessionRecordId: Long,
    ) {
        val exerciseSessionRecord = exerciseSessionRecordRepository.findByIdOrNull(exerciseSessionRecordId)
            ?: throw NotFoundExerciseSessionRecordException()

        exerciseSessionRecordRepository.delete(exerciseSessionRecord)
    }

    fun listByDate(
        userId: Long,
        date: LocalDate,
    ): List<ExerciseSessionRecordDto> {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundUserException()

        val exerciseSessionRecords = exerciseSessionRecordRepository.findAllByUserAndStartTimeBetween(
            user = user,
            startTime = date.atStartOfDay(),
            endTime = date.atTime(LocalDateTime.MAX.toLocalTime()),
        ) // todo QueryDsl

        return exerciseSessionRecordMapStruct.toDtos(exerciseSessionRecords)
    }

    fun findById(
        id: Long,
        userId: Long,
    ): ExerciseSessionRecordDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundUserException()

        val exerciseSessionRecord = exerciseSessionRecordRepository.findByUserAndId(
            user = user,
            id = id,
        ) ?: throw NotFoundExerciseSessionRecordException()

        return exerciseSessionRecordMapStruct.toDto(exerciseSessionRecord)
    }

    @Transactional
    fun create(
        userId: Long,
        memo: String,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        saveTypeId: Long,
    ): ExerciseSessionRecordDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundUserException()
        val saveType =
            exerciseSaveTypeRepository.findByIdOrNull(saveTypeId) ?: throw NotFoundExerciseSaveTypeException()
        val exerciseSessionRecord = ExerciseSessionRecord(
            user = user,
            memo = memo,
            startTime = startTime,
            endTime = endTime,
            saveType = saveType,
        ).run {
            exerciseSessionRecordRepository.save(this)
        }

        return exerciseSessionRecordMapStruct.toDto(exerciseSessionRecord)
    }

    @Transactional
    fun update(
        userId: Long,
        exerciseSessionRecordId: Long,
        memo: String,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        saveTypeId: Long,
    ): ExerciseSessionRecordDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundUserException()
        val saveType =
            exerciseSaveTypeRepository.findByIdOrNull(saveTypeId) ?: throw NotFoundExerciseSaveTypeException()
        val exerciseSessionRecord = exerciseSessionRecordRepository.findByIdOrNull(exerciseSessionRecordId)
            ?: throw NotFoundExerciseSessionRecordException()

        val updatedExerciseSessionRecord = exerciseSessionRecord.apply {
            this.user = user
            this.memo = memo
            this.startTime = startTime
            this.endTime = endTime
            this.saveType = saveType
        }.run {
            exerciseSessionRecordRepository.save(this)
        }

        return exerciseSessionRecordMapStruct.toDto(updatedExerciseSessionRecord)
    }
}

