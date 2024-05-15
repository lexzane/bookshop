package bookshop.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateOrderRequestDto(
        @NotBlank @Size(max = 255) String status
) {
}
