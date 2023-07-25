package uniapp.models.dto.mappers;

import uniapp.models.dto.RoleDto;
import uniapp.models.entities.Role;

public interface RoleMapper {

    Role dtoToEntity(RoleDto dto);
    RoleDto entityToDto(Role entity);

}
