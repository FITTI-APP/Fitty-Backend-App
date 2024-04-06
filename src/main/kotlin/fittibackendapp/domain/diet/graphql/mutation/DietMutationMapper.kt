package fittibackendapp.domain.diet.graphql.mutation

import fittibackendapp.domain.diet.facade.DietRecordQueryFacade
import fittibackendapp.dto.PcfRatioDto
import fittibackendapp.security.component.ArgumentResolver
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DietMutationMapper(
    private val dietRecordFacade: DietRecordQueryFacade,
    private val argumentResolver: ArgumentResolver
) {
    @MutationMapping
    fun getOrCreateTargetPcfRatio(): PcfRatioDto {
        val userId = argumentResolver.getUserId()

        return dietRecordFacade.getOrCreateTargetPcfRatio(userId)
    }
}
