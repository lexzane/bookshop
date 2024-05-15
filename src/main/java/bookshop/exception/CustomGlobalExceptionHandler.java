package bookshop.exception;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull final MethodArgumentNotValidException ex,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatusCode status,
            @NonNull final WebRequest request
    ) {
        final List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(this::buildErrorMessage)
                .collect(Collectors.toList());
        final CustomGlobalExceptionBody<List<String>> body =
                CustomGlobalExceptionBody.build(errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler({
            EntityNotFoundException.class,
            OrderValidationException.class,
            ConstraintViolationException.class,
            RegistrationException.class,
            DataIntegrityViolationException.class
    })
    private ResponseEntity<Object> handleCommonException(final Exception ex) {
        return buildResponse(CustomGlobalExceptionBody.build(ex.getMessage()));
    }

    @ExceptionHandler({AccessDeniedException.class})
    private ResponseEntity<Object> handleAccessDenied(final AccessDeniedException ex) {
        return buildResponse(CustomGlobalExceptionBody.build(FORBIDDEN, ex.getMessage()));
    }

    private ResponseEntity<Object> buildResponse(final CustomGlobalExceptionBody<String> body) {
        return new ResponseEntity<>(body, body.status());
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
