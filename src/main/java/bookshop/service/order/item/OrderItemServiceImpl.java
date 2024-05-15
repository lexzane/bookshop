package bookshop.service.order.item;

import bookshop.dto.order.item.OrderItemDto;
import bookshop.exception.EntityNotFoundException;
import bookshop.mapper.OrderItemMapper;
import bookshop.model.OrderItem;
import bookshop.repository.order.item.OrderItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemDto> findAllByOrderId(final Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId).stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto findByIdAndOrderId(final Long itemId, final Long orderId) {
        final OrderItem orderItem = orderItemRepository.findByIdAndOrderId(itemId, orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Couldn't get order item from DB by id=" + itemId));
        return orderItemMapper.toDto(orderItem);
    }
}
