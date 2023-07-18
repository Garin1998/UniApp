package uniapp.controllers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CourseReq(
        @JsonProperty("name")
        String name,
        @JsonProperty("ects")
        int ects,
        @JsonProperty("lecturerId")
        UUID lecturerId
) {}
