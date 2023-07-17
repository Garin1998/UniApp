package uniapp.models.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record LecturerDto(
        UUID id,
        String firstName,
        String lastName
) {}
