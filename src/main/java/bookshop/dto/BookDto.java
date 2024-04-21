package bookshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class BookDto {
    @NotNull
    @Positive
    private Long id;
    @NotBlank
    @Size(max = 255)
    private String title;
    @NotBlank
    @Size(max = 255)
    private String author;
    @NotBlank
    @Size(max = 255)
    @Pattern(regexp = "^\\d+(?:-?\\d+)*$", message = "must match digits or kebab case digits")
    private String isbn;
    @NotNull
    @Positive
    private BigDecimal price;
    @Size(max = 255)
    private String description;
    @Size(max = 255)
    private String coverImage;
}
