package fittibackendapp.dto.mapstruct

import fittibackendapp.domain.diet.entity.Nutrition
import fittibackendapp.dto.NutritionDto
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface DietMapStruct: GenericMapStruct<Nutrition, NutritionDto>
