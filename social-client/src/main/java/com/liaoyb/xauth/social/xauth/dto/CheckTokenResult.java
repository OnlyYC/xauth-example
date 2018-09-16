package com.liaoyb.xauth.social.xauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckTokenResult {
    @JsonProperty("user_name")
    private String userName;
}
