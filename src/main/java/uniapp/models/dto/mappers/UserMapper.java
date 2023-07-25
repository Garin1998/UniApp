package uniapp.models.dto.mappers;

import uniapp.models.dto.UserDto;
import uniapp.models.entities.User;

public interface UserMapper {

    User dtoToEntity(UserDto dto);
    UserDto entityToDto(User entity);

}
