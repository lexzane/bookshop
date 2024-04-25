package bookshop.controller;

import bookshop.dto.user.UserLoginRequestDto;
import bookshop.dto.user.UserLoginResponseDto;
import bookshop.dto.user.UserRegistrationRequestDto;
import bookshop.dto.user.UserResponseDto;
import bookshop.exception.RegistrationException;
import bookshop.security.AuthenticationService;
import bookshop.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication management", description = "Endpoints for managing authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Login as a user", description = "Login as a user")
    public UserLoginResponseDto login(@RequestBody @Valid final UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/registration")
    @Operation(summary = "Register a new user", description = "Register a new user")
    public UserResponseDto register(
            @RequestBody @Valid final UserRegistrationRequestDto requestDto
    ) throws RegistrationException {
        return userService.register(requestDto);
    }
}
