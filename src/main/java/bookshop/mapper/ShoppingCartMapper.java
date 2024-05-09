package bookshop.mapper;

import bookshop.dto.shopping.cart.ShoppingCartDto;
import bookshop.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "cartItems", source = "cartItems", qualifiedByName = "cartItemsToDto")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
