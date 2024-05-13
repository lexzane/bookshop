package bookshop.service.shopping.cart;

import bookshop.dto.cart.item.CreateCartItemRequestDto;
import bookshop.dto.shopping.cart.ShoppingCartDto;
import bookshop.mapper.CartItemMapper;
import bookshop.mapper.ShoppingCartMapper;
import bookshop.model.CartItem;
import bookshop.model.ShoppingCart;
import bookshop.model.User;
import bookshop.repository.shopping.cart.ShoppingCartRepository;
import bookshop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public void create(final User user) {
        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto findByUser(final String email) {
        final ShoppingCart shoppingCart = getShoppingCart(email);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Transactional
    @Override
    public void addCartItem(
            final String email,
            final CreateCartItemRequestDto requestDto
    ) {
        final ShoppingCart shoppingCart = getShoppingCart(email);
        final CartItem cartItem = cartItemMapper.toModel(requestDto);
        cartItem.setShoppingCart(shoppingCart);
        shoppingCart.getCartItems().add(cartItem);
        shoppingCartRepository.save(shoppingCart);
    }

    private ShoppingCart getShoppingCart(final String email) {
        final User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Couldn't find a user by email=" + email));
        return shoppingCartRepository.findByUserId(user.getId());
    }
}
