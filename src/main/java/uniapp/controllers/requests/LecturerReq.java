package uniapp.controllers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record LecturerReq(
        @JsonProperty("firstName")
        String firstName,
        @JsonProperty("lastName")
        String lastName
) {}
