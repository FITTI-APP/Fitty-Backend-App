package fittibackendapp.dto.mapstruct

import fittibackendapp.domain.follow.entity.Follow
import fittibackendapp.dto.FollowDto
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface FollowMapStruct: GenericMapStruct<Follow, FollowDto>
