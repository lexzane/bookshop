package bookshop.service.user;

import bookshop.dto.user.UserRegistrationRequestDto;
import bookshop.dto.user.UserResponseDto;
import bookshop.exception.RegistrationException;
import bookshop.mapper.UserMapper;
import bookshop.model.User;
import bookshop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.email())) {
            throw new RegistrationException("User with this email already exists");
        }
        final User user = User.builder()
                .email(requestDto.email())
                .password(requestDto.password())
                .firstName(requestDto.firstName())
                .lastName(requestDto.lastName())
                .shippingAddress(requestDto.shippingAddress())
                .build();
        return userMapper.toDto(userRepository.save(user));
    }
}
