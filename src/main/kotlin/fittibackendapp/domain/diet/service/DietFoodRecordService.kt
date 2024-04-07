package fittibackendapp.domain.diet.service

import fittibackendapp.domain.diet.entity.DietFoodRecord
import fittibackendapp.domain.diet.repository.DietFoodRecordRepository
import fittibackendapp.domain.diet.repository.DietMealRecordRepository
import fittibackendapp.domain.diet.repository.NutritionRepository
import fittibackendapp.dto.DietFoodRecordDto
import fittibackendapp.dto.mapstruct.DietFoodRecordMapStruct
import fittibackendapp.exception.NotFoundDietMealRecordException
import fittibackendapp.exception.NotFoundNutritionException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DietFoodRecordService(
    private val dietFoodRecordRepository: DietFoodRecordRepository,
    private val dietMealRecordRepository: DietMealRecordRepository,
    private val nutritionRepository: NutritionRepository,
    private val dietFoodRecordMapStruct: DietFoodRecordMapStruct
) {
    fun findAllByDietMealRecordId(dietMealRecordId: Long): List<DietFoodRecordDto> {
        val dietFoodRecords = dietFoodRecordRepository.findAllByDietMealRecordId(dietMealRecordId)
        return dietFoodRecordMapStruct.toDtos(dietFoodRecords)
    }

    fun findAllByDietMealRecordIds(dietMealRecordIds: List<Long>): List<DietFoodRecordDto> {
        val dietFoodRecords = dietFoodRecordRepository.findAllByDietMealRecordIdIn(dietMealRecordIds)
        return dietFoodRecordMapStruct.toDtos(dietFoodRecords)
    }

    @Transactional
    fun create(
        dietMealRecordId: Long,
        weight: Long,
        nutritionId: Long
    ): DietFoodRecordDto {
        val dietMealRecord = dietMealRecordRepository.findByIdOrNull(dietMealRecordId)
            ?: throw NotFoundDietMealRecordException()
        val nutrition = nutritionRepository.findByIdOrNull(nutritionId)
            ?: throw NotFoundNutritionException()

        val dietFoodRecord = DietFoodRecord(
            dietMealRecord = dietMealRecord,
            weight = weight,
            nutrition = nutrition,
        ).run {
            dietFoodRecordRepository.save(this)
        }

        return dietFoodRecordMapStruct.toDto(dietFoodRecord)
    }
}
