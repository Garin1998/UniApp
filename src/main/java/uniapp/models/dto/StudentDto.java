package uniapp.models.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record StudentDto(
        UUID id,
        String firstName,
        String lastName,
        int term
) {}
