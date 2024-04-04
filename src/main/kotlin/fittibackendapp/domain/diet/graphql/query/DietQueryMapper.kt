package fittibackendapp.domain.diet.graphql.query

import fittibackendapp.domain.diet.facade.DietRecordQueryFacade
import fittibackendapp.dto.PcfAmountInGramsDto
import fittibackendapp.dto.PcfRatioDto
import fittibackendapp.security.component.ArgumentResolver
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class DietQueryMapper(
    private val dietRecordQueryFacade: DietRecordQueryFacade,
    private val argumentResolver: ArgumentResolver
) {
    @QueryMapping
    fun getPcfAmountInGramsBetweenDays(
        @Argument
        fromDate: LocalDate,
        @Argument
        toDate: LocalDate
    ): PcfAmountInGramsDto {
        val userId = argumentResolver.getUserId()

        return dietRecordQueryFacade.getPcfAmountInGramsBetweenDays(
            userId = userId,
            fromDate = fromDate,
            toDate = toDate,
        )
    }

    @QueryMapping
    fun getPcfRatioInKcalBetweenDays(
        @Argument
        fromDate: LocalDate,
        @Argument
        toDate: LocalDate
    ): PcfRatioDto {
        val userId = argumentResolver.getUserId()

        return dietRecordQueryFacade.getPcfRatioInKcalBetweenDays(
            userId = userId,
            fromDate = fromDate,
            toDate = toDate,
        )
    }

    @QueryMapping
    fun getTargetPcfRatio(): PcfRatioDto {
        val userId = argumentResolver.getUserId()

        return dietRecordQueryFacade.getTargetPcfRatio(userId)
    }
}
