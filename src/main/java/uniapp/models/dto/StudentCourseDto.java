package uniapp.models.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record StudentCourseDto(
        UUID id,
        StudentDto studentDto,
        CourseDto courseDto,
        double degree
) {}
