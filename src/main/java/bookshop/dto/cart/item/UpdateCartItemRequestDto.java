package bookshop.dto.cart.item;

import jakarta.validation.constraints.Positive;

public record UpdateCartItemRequestDto(
        @Positive int quantity
) {
}
