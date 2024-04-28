package bookshop.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequestDto(
        @NotBlank @Size(max = 25) String name,
        @NotBlank @Size(max = 255) String description
) {
}
