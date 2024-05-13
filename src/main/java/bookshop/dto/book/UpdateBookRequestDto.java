package bookshop.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import org.hibernate.validator.constraints.ISBN;

public record UpdateBookRequestDto(
        @NotBlank @Size(max = 255) String title,
        @NotBlank @Size(max = 255) String author,
        @NotBlank @ISBN String isbn,
        @NotNull @Positive BigDecimal price,
        @Size(max = 255) String description,
        @Size(max = 255) String coverImage,
        @NotNull @Size(min = 1) Set<Long> categoryIds
) {
}
