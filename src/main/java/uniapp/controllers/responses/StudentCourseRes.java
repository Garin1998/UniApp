package uniapp.controllers.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import uniapp.models.dto.CourseDto;

import java.util.UUID;

@Builder
public record StudentCourseRes(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("course")
        CourseDto courseDto,
        @JsonProperty("degree")
        double degree
) {}
