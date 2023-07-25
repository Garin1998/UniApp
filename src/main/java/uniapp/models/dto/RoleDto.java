package uniapp.models.dto;

import lombok.Builder;
import uniapp.models.ERole;

import java.util.UUID;

@Builder
public record RoleDto(
        UUID id,
        ERole name
) {}
