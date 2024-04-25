package bookshop.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

record CustomGlobalExceptionBody<T>(
        LocalDateTime timestamp,
        HttpStatusCode status,
        T errors
) {
    static <T> CustomGlobalExceptionBody<T> build(final T errors) {
        return new CustomGlobalExceptionBody<>(LocalDateTime.now(), BAD_REQUEST, errors);
    }

    static <T> CustomGlobalExceptionBody<T> build(final HttpStatus httpStatus, final T errors) {
        return new CustomGlobalExceptionBody<>(LocalDateTime.now(), httpStatus, errors);
    }
}
