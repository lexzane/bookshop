package bookshop.mapper;

import bookshop.dto.user.UserResponseDto;
import bookshop.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toDto(User user);
}
