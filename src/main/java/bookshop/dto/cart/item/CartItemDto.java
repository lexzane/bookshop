package bookshop.dto.cart.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CartItemDto {
    @NotNull
    @Positive
    private Long id;
    @NotNull
    @Positive
    private Long bookId;
    @NotBlank
    @Size(max = 255)
    private String bookTitle;
    @Positive
    private int quantity;
}
