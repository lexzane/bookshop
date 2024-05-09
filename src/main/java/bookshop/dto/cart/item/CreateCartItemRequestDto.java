package bookshop.dto.cart.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCartItemRequestDto(
        @NotNull @Positive Long bookId,
        @Positive int quantity
) {
}
