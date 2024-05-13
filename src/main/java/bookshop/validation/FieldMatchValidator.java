package bookshop.validation;

import bookshop.dto.user.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class FieldMatchValidator
        implements ConstraintValidator<FieldMatch, UserRegistrationRequestDto> {
    @Override
    public boolean isValid(final UserRegistrationRequestDto requestDto,
                           final ConstraintValidatorContext context) {
        if (requestDto == null) {
            return false;
        }
        if (StringUtils.isBlank(requestDto.password())
                || StringUtils.isBlank(requestDto.repeatPassword())) {
            return false;
        }
        return requestDto.password().equals(requestDto.repeatPassword());
    }
}
