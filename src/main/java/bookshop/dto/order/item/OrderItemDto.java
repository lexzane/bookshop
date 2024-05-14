package bookshop.dto.order.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemDto {
    @NotNull
    @Positive
    private Long id;
    @NotNull
    @Positive
    private Long bookId;
    @Positive
    private int quantity;
}
