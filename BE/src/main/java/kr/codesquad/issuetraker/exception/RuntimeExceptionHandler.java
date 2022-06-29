package kr.codesquad.issuetraker.exception;

import kr.codesquad.issuetraker.dto.GeneralResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RuntimeExceptionHandler {
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<GeneralResponseDto> handleResourceNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponseDto(404, e.getMessage()));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<GeneralResponseDto> handleIllegalArgumentException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponseDto(400, "올바르지 않은 요청입니다. 누락되거나 잘못된 요청값이 있는지 확인해주세요."));
    }
    @ExceptionHandler(value = PasswordNotMatchedException.class)
    public ResponseEntity<GeneralResponseDto> handlePasswordNotMatchedException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GeneralResponseDto(401, e.getMessage()));
    }

    @ExceptionHandler(value = InvalidOauthClientNameException.class)
    public ResponseEntity<GeneralResponseDto> handleInvalidOauthClientNameException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponseDto(400, e.getMessage()));
    }
}
