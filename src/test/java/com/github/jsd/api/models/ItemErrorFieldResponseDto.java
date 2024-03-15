package com.github.jsd.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemErrorFieldResponseDto {

    private String method;
    private String status;
    private String error;
    private String message;

    @JsonProperty("field_error")
    private String errorField;
}
