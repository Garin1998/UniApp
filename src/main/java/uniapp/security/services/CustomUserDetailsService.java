package uniapp.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uniapp.models.dto.mappers.UserMapper;
import uniapp.repositories.UserRepository;

import static uniapp.constants.ErrorMessages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return (userMapper
                .entityToDto(userRepository
                        .findByName(username).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND))
                )
        );

    }

}
