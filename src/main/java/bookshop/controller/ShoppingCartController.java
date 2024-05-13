package bookshop.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import bookshop.dto.cart.item.CreateCartItemRequestDto;
import bookshop.dto.cart.item.UpdateCartItemRequestDto;
import bookshop.dto.shopping.cart.ShoppingCartDto;
import bookshop.service.cart.item.CartItemService;
import bookshop.service.shopping.cart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping carts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    @Operation(summary = "Get a shopping cart",
            description = "Get an existing shopping cart by user")
    public ShoppingCartDto getShoppingCartById(final Authentication authentication) {
        return shoppingCartService.findByUser(authentication.getName());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Add a book to cart", description = "Add a book to cart")
    public void addBookToShoppingCart(
            final Authentication authentication,
            @RequestBody @Valid final CreateCartItemRequestDto requestDto
    ) {
        shoppingCartService.addCartItem(authentication.getName(), requestDto);
    }

    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(NO_CONTENT)
    @PutMapping(value = "/cart-items/{cartItemId}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a cart item", description = "Update a cart item by id")
    public void updateCartItemById(
            @PathVariable @Valid @Positive final Long cartItemId,
            @RequestBody @Valid final UpdateCartItemRequestDto requestDto
    ) {
        cartItemService.updateById(cartItemId, requestDto);
    }

    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Delete a cart item", description = "Delete a cart item by id")
    public void deleteCartItemById(@PathVariable @Valid @Positive final Long cartItemId) {
        cartItemService.deleteById(cartItemId);
    }
}
