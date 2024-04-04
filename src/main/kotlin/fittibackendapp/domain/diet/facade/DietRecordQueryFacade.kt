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
    fun getPcfAmountInGramsBetweenDays(
        userId: Long,
        fromDate: LocalDate,
        toDate: LocalDate
    ): PcfAmountInGramsDto {
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

    fun getPcfRatioInKcalBetweenDays(userId: Long, fromDate: LocalDate, toDate: LocalDate): PcfRatioDto {
        val pcfAmountInGrams = getPcfAmountInGramsBetweenDays(
            userId = userId,
            fromDate = fromDate,
            toDate = toDate,
        )
        val totalAmountInKcal =
            pcfAmountInGrams.protein * 4 + pcfAmountInGrams.carbohydrate * 4 + pcfAmountInGrams.fat * 9
        if (totalAmountInKcal == 0.0) {
            return PcfRatioDto(
                proteinRatio = 0.0,
                carbohydrateRatio = 0.0,
                fatRatio = 0.0,
            )
        }
        return PcfRatioDto(
            proteinRatio = pcfAmountInGrams.protein * 4 / totalAmountInKcal,
            carbohydrateRatio = pcfAmountInGrams.carbohydrate * 4 / totalAmountInKcal,
            fatRatio = pcfAmountInGrams.fat * 9 / totalAmountInKcal,
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
