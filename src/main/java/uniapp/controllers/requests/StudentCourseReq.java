package uniapp.controllers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

@Builder
public record StudentCourseReq(
        @JsonProperty("studentId")
        UUID studentId,
        @JsonProperty("courseId")
        UUID courseId,
        @JsonProperty("degree")
        double degree
) {}
