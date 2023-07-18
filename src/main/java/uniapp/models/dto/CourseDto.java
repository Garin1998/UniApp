package uniapp.models.dto;

import lombok.Builder;
import uniapp.models.entities.Lecturer;

import java.util.UUID;

@Builder
public record CourseDto(
        UUID id,
        String name,
        int ects,
        LecturerDto lecturer
) {}
