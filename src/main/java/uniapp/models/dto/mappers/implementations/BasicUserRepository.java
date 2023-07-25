package uniapp.models.dto.mappers.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uniapp.models.dto.UserDto;
import uniapp.models.dto.mappers.RoleMapper;
import uniapp.models.dto.mappers.UserMapper;
import uniapp.models.entities.User;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BasicUserRepository implements UserMapper {

    private final RoleMapper roleMapper;

    @Override
    public User dtoToEntity(UserDto dto) {

        return User.builder()
                .id(dto.id())
                .name(dto.name())
                .password(dto.password())
                .userRoles(dto.roles().stream().map(roleMapper::dtoToEntity).collect(Collectors.toSet()))
                .registrationTimestamp(dto.registrationTimestamp())
                .build();

    }

    @Override
    public UserDto entityToDto(User entity) {

        return UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .password(entity.getPassword())
                .roles(entity.getUserRoles().stream().map(roleMapper::entityToDto).collect(Collectors.toSet()))
                .registrationTimestamp(entity.getRegistrationTimestamp())
                .build();

    }
}
