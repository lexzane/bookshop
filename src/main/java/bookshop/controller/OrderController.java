package bookshop.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import bookshop.dto.order.CreateOrderRequestDto;
import bookshop.dto.order.OrderDto;
import bookshop.dto.order.UpdateOrderRequestDto;
import bookshop.dto.order.item.OrderItemDto;
import bookshop.service.order.OrderService;
import bookshop.service.order.item.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order management", description = "Endpoints for managing orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Complete an order", description = "Complete an order")
    public OrderDto completeOrder(
            final Authentication authentication,
            @RequestBody @Valid final CreateOrderRequestDto requestDto
    ) {
        return orderService.completeOrder(authentication.getName(), requestDto);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    @Operation(summary = "Get an order history", description = "Get an existing order history")
    public List<OrderDto> getOrderHistory(final Authentication authentication) {
        return orderService.getOrderHistory(authentication.getName());
    }

    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(NO_CONTENT)
    @PatchMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an order status", description = "Update an order status by id")
    public void updateOrderStatusById(
            @PathVariable @Valid @Positive final Long id,
            @RequestBody @Valid final UpdateOrderRequestDto requestDto
    ) {
        orderService.updateOrderStatusById(id, requestDto);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{orderId}/items")
    @Operation(
            summary = "Get all order items for a specific order",
            description = "Get all order items for a specific order by id"
    )
    public List<OrderItemDto> getOrderItemsById(
            @PathVariable @Valid @Positive final Long orderId
    ) {
        return orderItemService.findAllByOrderId(orderId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(
            summary = "Get a specific order item within an order",
            description = "Get a specific order item within an order by id"
    )
    public OrderItemDto getOrderItemById(
            @PathVariable @Valid @Positive final Long orderId,
            @PathVariable @Valid @Positive final Long itemId
    ) {
        return orderItemService.findByIdAndOrderId(itemId, orderId);
    }
}
