package uniapp.models.dto.mappers.implementations;

import org.springframework.stereotype.Component;
import uniapp.models.dto.RoleDto;
import uniapp.models.dto.mappers.RoleMapper;
import uniapp.models.entities.Role;

@Component
public class BasicRoleMapper implements RoleMapper {

    @Override
    public Role dtoToEntity(RoleDto dto) {

        return Role.builder()
                .id(dto.id())
                .name(dto.name())
                .build();

    }

    @Override
    public RoleDto entityToDto(Role entity) {

        return RoleDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();

    }

}
