package fittibackendapp.domain.diet.facade

import fittibackendapp.domain.diet.service.DietFoodRecordService
import fittibackendapp.domain.diet.service.DietMealRecordService
import fittibackendapp.domain.diet.service.NutritionService
import fittibackendapp.domain.diet.service.TargetPcfRatioService
import fittibackendapp.dto.PcfAmountInGramsDto
import fittibackendapp.dto.PcfRatioDto
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DietRecordQueryFacade(
    private val nutritionService: NutritionService,
    private val dietMealRecordService: DietMealRecordService,
    private val dietFoodRecordService: DietFoodRecordService,
    private val targetPcfRatioService: TargetPcfRatioService
) {
    fun getPcfAmountInGramsBetweenDays(userId: Long, fromDate: LocalDate, toDate: LocalDate): PcfAmountInGramsDto {
        val dietMealRecordIds =
            dietMealRecordService.findDietMealRecordsBetweenDays(
                userId = userId,
                fromDate = fromDate,
                toDate = toDate,
            ).map { it.id }
        val dietFoodRecords = dietFoodRecordService.findAllByDietMealRecordIds(dietMealRecordIds)
        val nutritions = nutritionService.findAllByIds(
            dietFoodRecords.map {
                it.nutrition.id
            }.distinct(),
        )
        val nutritionMap = nutritions.associateBy { it.id }
        var protein = 0.0
        var carbohydrate = 0.0
        var fat = 0.0
        dietFoodRecords.forEach {
            val nutrition = nutritionMap[it.nutrition.id]!!
            protein += nutrition.protein * it.weight
            carbohydrate += nutrition.carbohydrate * it.weight
            fat += nutrition.fat * it.weight
        }
        return PcfAmountInGramsDto(
            protein = protein,
            carbohydrate = carbohydrate,
            fat = fat,
        )
    }

    fun getPcfRatioInGramsBetweenDays(userId: Long, fromDate: LocalDate, toDate: LocalDate): PcfRatioDto {
        val pcfAmountInGrams = getPcfAmountInGramsBetweenDays(
            userId = userId,
            fromDate = fromDate,
            toDate = toDate,
        )
        val totalAmountInGrams = pcfAmountInGrams.protein + pcfAmountInGrams.carbohydrate + pcfAmountInGrams.fat
        return PcfRatioDto(
            pcfAmountInGrams.protein / totalAmountInGrams,
            pcfAmountInGrams.carbohydrate / totalAmountInGrams,
            pcfAmountInGrams.fat / totalAmountInGrams,
        )
    }

    fun getTargetPcfRatio(userId: Long): PcfRatioDto {
        val targetPcfRatio = targetPcfRatioService.findByUserId(userId)
        return PcfRatioDto(
            proteinRatio = targetPcfRatio.protein,
            carbohydrateRatio = targetPcfRatio.carbohydrate,
            fatRatio = targetPcfRatio.fat,
        )
    }
}
