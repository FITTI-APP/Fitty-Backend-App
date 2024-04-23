package fittibackendapp.dto.mapstruct

import fittibackendapp.domain.body.entity.UserDetail
import fittibackendapp.dto.UserDetailDto
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface UserDetailMapStruct: GenericMapStruct<UserDetail, UserDetailDto>
