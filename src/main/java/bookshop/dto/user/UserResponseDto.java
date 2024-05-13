package bookshop.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UserResponseDto(
        @NotNull @Positive Long id,
        @NotBlank @Size(min = 6, max = 30) @Email String email,
        @NotBlank @Size(max = 30) String firstName,
        @NotBlank @Size(max = 30) String lastName,
        @NotBlank @Size(max = 255) String shippingAddress
) {
}
