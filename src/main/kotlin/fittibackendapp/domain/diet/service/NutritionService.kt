package fittibackendapp.domain.diet.service

import fittibackendapp.domain.diet.repository.NutritionRepository
import fittibackendapp.dto.NutritionDto
import fittibackendapp.dto.mapstruct.NutritionMapStruct
import fittibackendapp.exception.NotFoundNutritionException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class NutritionService(
    private val nutritionRepository: NutritionRepository,
    private val nutritionMapStruct: NutritionMapStruct
) {
    fun findById(id: Long): NutritionDto {
        val nutrition = nutritionRepository.findByIdOrNull(id) ?: throw NotFoundNutritionException()
        return nutritionMapStruct.toDto(nutrition)
    }

    fun findAllByIds(ids: List<Long>): List<NutritionDto> {
        val nutritions = nutritionRepository.findAllById(ids)
        return nutritionMapStruct.toDtos(nutritions)
    }
}
