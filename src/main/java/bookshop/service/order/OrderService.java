package bookshop.service.order;

import bookshop.dto.order.CreateOrderRequestDto;
import bookshop.dto.order.OrderDto;
import bookshop.dto.order.UpdateOrderRequestDto;
import java.util.List;

public interface OrderService {
    OrderDto completeOrder(String email, CreateOrderRequestDto requestDto);

    List<OrderDto> getOrderHistory(String email);

    void updateOrderStatusById(Long id, UpdateOrderRequestDto requestDto);
}
