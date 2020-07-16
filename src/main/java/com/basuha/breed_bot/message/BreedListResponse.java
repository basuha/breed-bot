package com.basuha.breed_bot.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "message",
        "status"
})
@ToString
public class BreedListResponse {
    @JsonProperty("message")
    private BreedList message;
    @JsonProperty("status")
    private String status;
}
