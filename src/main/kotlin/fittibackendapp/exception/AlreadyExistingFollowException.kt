package fittibackendapp.exception;

import fittibackendapp.common.exception.base.ResponseStatusReasonException;
import org.springframework.http.HttpStatus;

class AlreadyExistingFollowException: ResponseStatusReasonException(
    statusCode = HttpStatus.NOT_ACCEPTABLE,
    reasonName = "ALREADY_EXISTING_FOLLOW",
    reasonMessage = "이미 존재하는 팔로우 정보 입니다.",
)
