package bookshop.service.cart.item;

import bookshop.dto.cart.item.UpdateCartItemRequestDto;

public interface CartItemService {
    void updateById(Long id, UpdateCartItemRequestDto requestDto);

    void deleteById(Long id);
}
