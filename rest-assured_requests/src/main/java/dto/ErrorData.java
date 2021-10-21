package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorData(
        @JsonProperty("email or password")
        String emailOrPassword) {
}
