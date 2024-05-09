package bookshop.mapper;

import bookshop.dto.cart.item.CartItemDto;
import bookshop.dto.cart.item.CreateCartItemRequestDto;
import bookshop.dto.cart.item.UpdateCartItemRequestDto;
import bookshop.model.CartItem;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "book", source = "bookId", qualifiedByName = "bookFromId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shoppingCart", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    CartItem toModel(CreateCartItemRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shoppingCart", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateModelFromDto(@MappingTarget CartItem cartItem, UpdateCartItemRequestDto requestDto);

    @Named("cartItemsToDto")
    default List<CartItemDto> cartItemsToDto(final Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::toDto)
                .sorted(Comparator.comparing(CartItemDto::getId))
                .toList();
    }
}
