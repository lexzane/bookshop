package bookshop.mapper;

import bookshop.dto.order.item.OrderItemDto;
import bookshop.model.CartItem;
import bookshop.model.Order;
import bookshop.model.OrderItem;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(target = "price", source = "cartItem.book.price")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    OrderItem toModelFromCartItem(CartItem cartItem, Order order);

    @Named("orderItemsToDto")
    default List<OrderItemDto> orderItemsToDto(final Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(this::toDto)
                .sorted(Comparator.comparing(OrderItemDto::getId))
                .toList();
    }
}
