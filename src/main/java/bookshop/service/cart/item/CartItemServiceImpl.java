package bookshop.service.cart.item;

import bookshop.dto.cart.item.UpdateCartItemRequestDto;
import bookshop.exception.EntityNotFoundException;
import bookshop.mapper.CartItemMapper;
import bookshop.model.CartItem;
import bookshop.repository.cart.item.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public void updateById(final Long id, final UpdateCartItemRequestDto requestDto) {
        final CartItem cartItem = cartItemRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Couldn't get cart item from DB by id=" + id));
        cartItemMapper.updateModelFromDto(cartItem, requestDto);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteById(final Long id) {
        cartItemRepository.deleteById(id);
    }
}
