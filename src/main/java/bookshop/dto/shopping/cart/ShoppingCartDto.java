package bookshop.dto.shopping.cart;

import bookshop.dto.cart.item.CartItemDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class ShoppingCartDto {
    @NotNull
    @Positive
    private Long id;
    @NotNull
    @Positive
    @ISBN
    private Long userId;
    @NotNull
    @Size(min = 1)
    @UniqueElements
    private List<CartItemDto> cartItems;
}
