package bookshop.dto.order;

import bookshop.dto.order.item.OrderItemDto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public final class OrderDto {
    @NotNull
    @Positive
    private Long id;
    @NotNull
    @Positive
    private Long userId;
    @NotNull
    @Size(min = 1)
    @UniqueElements
    private List<OrderItemDto> orderItems;
    @NotNull
    @PastOrPresent
    private LocalDateTime orderDate;
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal total;
    @NotBlank
    private String status;
}
