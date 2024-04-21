package bookshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreateBookRequestDto(
        @NotBlank @Size(max = 255) String title,
        @NotBlank @Size(max = 255) String author,
        @NotBlank @Size(max = 255) @Pattern(regexp = "^\\d+(?:-?\\d+)*$",
                message = "must match digits or kebab case digits")
        String isbn,
        @NotNull @Positive BigDecimal price,
        @Size(max = 255) String description,
        @Size(max = 255) String coverImage
) {
}
