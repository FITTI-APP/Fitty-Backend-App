package fittibackendapp.exception

import fittibackendapp.common.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class InvalidExerciseSessionInputException: ResponseStatusReasonException(
    statusCode = HttpStatus.BAD_REQUEST,
    reasonName = "INVALID_EXERCISE_SESSION_INPUT",
    reasonMessage = "운동 세션 입력이 올바르지 않습니다.",
)
