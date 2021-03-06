package pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    // HTTP STATUS 409
    @ExceptionHandler({ResourceConflictException.class}) // todo ps czy działaja logi do pliku
    @ResponseStatus(value = HttpStatus.CONFLICT)
    ErrorResponse handleConflict(ResourceConflictException e) {
        logger.warn("409 Conflict", e);
        return new ErrorResponse(e.getMessage(), LocalDateTime.now());
    }

    // HTTP STATUS 404
    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleNotFound(NotFoundException e) {
        logger.warn("404 Not Found", e);
        return new ErrorResponse(e.getMessage(), LocalDateTime.now());
    }

    // HTTP STATUS 400
    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleBadRequest(BadRequestException e) {
        logger.warn("400 Bad Request", e);
        return new ErrorResponse(e.getMessage(), LocalDateTime.now());
    }

    // HTTP STATUS 500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({NullPointerException.class})
    ErrorResponse handleNpe(NullPointerException e) {
        logger.warn("500 Null Pointer Exception", e);
        return new ErrorResponse(e.getMessage(), LocalDateTime.now());
    }

    @Data
    private static class ErrorResponse {
        private final String message;
        private final LocalDateTime localDateTime;
    }
}
