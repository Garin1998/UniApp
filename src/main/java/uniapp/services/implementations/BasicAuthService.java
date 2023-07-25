package uniapp.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uniapp.controllers.requests.AuthReq;
import uniapp.controllers.requests.RegisterReq;
import uniapp.controllers.responses.AuthResponse;
import uniapp.models.ERole;
import uniapp.models.dto.mappers.UserMapper;
import uniapp.models.entities.Role;
import uniapp.models.entities.User;
import uniapp.models.exceptions.RoleNotFound;
import uniapp.repositories.RoleRepository;
import uniapp.repositories.UserRepository;
import uniapp.security.services.JwtService;
import uniapp.services.AuthService;

import java.util.HashSet;
import java.util.Set;

import static uniapp.constants.ErrorMessages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserMapper userMapper;

    public AuthResponse register(RegisterReq request) {

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository
                .findByName(ERole.ROLE_USER)
                .orElseThrow(RoleNotFound::new);

        roles.add(userRole);

        User user = User.builder()
                .name(request.name())
                .password(passwordEncoder.encode(request.password()))
                .userRoles(roles)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(userMapper.entityToDto(user));

        return new AuthResponse(token);

    }

    public AuthResponse authenticate(AuthReq request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.name(),
                        request.password()
                )
        );

        User user = userRepository.findByName(
                request.name()).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND)
        );

        String token = jwtService.generateToken(userMapper.entityToDto(user));

        return new AuthResponse(token);

    }

}
