package uniapp.controllers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import static uniapp.constants.ErrorMessages.NOT_BLANK;

@Builder
public record RegisterReq(
        @JsonProperty("name")
        @NotBlank(message = NOT_BLANK)
        String name,
        @JsonProperty("password")
        String password
) {}
