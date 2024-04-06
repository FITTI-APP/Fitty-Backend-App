package fittibackendapp.domain.diet.service

import fittibackendapp.domain.auth.repository.UserRepository
import fittibackendapp.domain.diet.entity.DietMealRecord
import fittibackendapp.domain.diet.repository.DietMealRecordRepository
import fittibackendapp.dto.DietMealRecordDto
import fittibackendapp.dto.mapstruct.DietMealRecordMapStruct
import fittibackendapp.exception.NotFoundUserException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class DietMealRecordService(
    private val dietMealRecordRepository: DietMealRecordRepository,
    private val dietMealRecordMapStruct: DietMealRecordMapStruct,
    private val userRepository: UserRepository
) {
    fun findDietMealRecordsBetweenDays(
        userId: Long,
        fromDate: LocalDate,
        toDate: LocalDate
    ): List<DietMealRecordDto> {
        val dietMealRecords = dietMealRecordRepository.findDietMealRecordsBetweenDays(
            userId = userId,
            fromDate = fromDate.atStartOfDay(),
            toDate = toDate.atTime(LocalDateTime.MAX.toLocalTime()),
        )
        return dietMealRecordMapStruct.toDtos(dietMealRecords)
    }

    @Transactional
    fun create(
        userId: Long,
        dateTime: LocalDateTime,
        memo: String
    ): DietMealRecordDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundUserException()
        val dietMealRecord = DietMealRecord(
            user = user,
            dateTime = dateTime,
            memo = memo,
        ).run {
            dietMealRecordRepository.save(this)
        }

        return dietMealRecordMapStruct.toDto(dietMealRecord)
    }
}
