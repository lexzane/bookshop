package bookshop.service.order;

import static java.math.BigDecimal.ZERO;

import bookshop.dto.order.CreateOrderRequestDto;
import bookshop.dto.order.OrderDto;
import bookshop.dto.order.UpdateOrderRequestDto;
import bookshop.exception.EntityNotFoundException;
import bookshop.exception.OrderValidationException;
import bookshop.mapper.OrderItemMapper;
import bookshop.mapper.OrderMapper;
import bookshop.model.Order;
import bookshop.model.OrderItem;
import bookshop.model.ShoppingCart;
import bookshop.repository.order.OrderRepository;
import bookshop.repository.shopping.cart.ShoppingCartRepository;
import bookshop.service.shopping.cart.ShoppingCartService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartService shoppingCartService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    @Override
    public OrderDto completeOrder(
            final String email,
            final CreateOrderRequestDto requestDto
    ) {
        final ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(email);
        if (shoppingCart.getCartItems().isEmpty()) {
            throw new OrderValidationException("Shopping cart items not found");
        }
        final Order order = orderMapper.toModel(requestDto);
        order.setUser(shoppingCart.getUser());
        final Set<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(cartItem -> orderItemMapper.toModelFromCartItem(cartItem, order))
                .collect(Collectors.toSet());
        order.setOrderItems(orderItems);
        final BigDecimal total = shoppingCart.getCartItems().stream()
                .map(cartItem -> cartItem.getBook().getPrice())
                .reduce(ZERO, BigDecimal::add);
        order.setTotal(total);
        orderRepository.save(order);
        shoppingCartService.clear(shoppingCart);
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getOrderHistory(final String email) {
        return orderRepository.findAllByUserEmail(email).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public void updateOrderStatusById(final Long id, final UpdateOrderRequestDto requestDto) {
        final Order order = orderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Couldn't get order from DB by id=" + id));
        orderMapper.updateModelFromDto(order, requestDto);
        orderRepository.save(order);
    }
}
