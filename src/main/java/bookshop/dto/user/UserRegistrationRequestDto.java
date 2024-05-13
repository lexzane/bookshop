package bookshop.dto.user;

import bookshop.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@FieldMatch(first = "password", second = "repeatPassword")
public record UserRegistrationRequestDto(
        @NotBlank @Size(min = 6, max = 30) @Email String email,
        @NotBlank @Size(min = 8, max = 30) String password,
        @NotBlank @Size(min = 8, max = 30) String repeatPassword,
        @NotBlank @Size(max = 30) String firstName,
        @NotBlank @Size(max = 30) String lastName,
        @NotBlank @Size(max = 255) String shippingAddress
) {
}
