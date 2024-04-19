package bookshop.exception;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String ERRORS_PROPERTY = "errors";
    private static final String TIMESTAMP_PROPERTY = "timestamp";
    private static final String STATUS_PROPERTY = "status";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull final MethodArgumentNotValidException ex,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatusCode status,
            @NonNull final WebRequest request
    ) {
        final LinkedHashMap<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_PROPERTY, LocalDateTime.now());
        body.put(STATUS_PROPERTY, status);
        final List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(this::buildErrorMessage)
                .collect(Collectors.toList());
        body.put(ERRORS_PROPERTY, errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<Object> handleConstraintViolation(
            final ConstraintViolationException ex
    ) {
        final LinkedHashMap<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_PROPERTY, LocalDateTime.now());
        body.put(STATUS_PROPERTY, BAD_REQUEST);
        body.put(ERRORS_PROPERTY, ex.getMessage());
        return new ResponseEntity<>(body, BAD_REQUEST);
    }

    private String buildErrorMessage(final ObjectError error) {
        if (error instanceof FieldError fieldError) {
            final String field = fieldError.getField();
            final String message = fieldError.getDefaultMessage();
            return field + SPACE + message;
        }
        return error.getDefaultMessage();
    }
}
