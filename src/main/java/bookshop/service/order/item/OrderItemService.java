package bookshop.service.order.item;

import bookshop.dto.order.item.OrderItemDto;
import java.util.List;

public interface OrderItemService {
    List<OrderItemDto> findAllByOrderId(Long orderId);

    OrderItemDto findByIdAndOrderId(Long itemId, Long orderId);
}
