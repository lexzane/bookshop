package bookshop.mapper;

import bookshop.dto.order.CreateOrderRequestDto;
import bookshop.dto.order.OrderDto;
import bookshop.dto.order.UpdateOrderRequestDto;
import bookshop.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "orderItems", source = "orderItems", qualifiedByName = "orderItemsToDto")
    OrderDto toDto(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Order toModel(CreateOrderRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "shippingAddress", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateModelFromDto(@MappingTarget Order order, UpdateOrderRequestDto requestDto);
}
