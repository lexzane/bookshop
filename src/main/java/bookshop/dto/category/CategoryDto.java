package bookshop.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
    @NotNull
    @Positive
    private Long id;
    @NotBlank
    @Size(max = 25)
    private String name;
    @NotBlank
    @Size(max = 255)
    private String description;
}
