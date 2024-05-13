package bookshop.service.shopping.cart;

import bookshop.dto.cart.item.CreateCartItemRequestDto;
import bookshop.dto.shopping.cart.ShoppingCartDto;
import bookshop.model.User;

public interface ShoppingCartService {
    void create(User user);

    ShoppingCartDto findByUser(String email);

    void addCartItem(String email, CreateCartItemRequestDto requestDto);
}
