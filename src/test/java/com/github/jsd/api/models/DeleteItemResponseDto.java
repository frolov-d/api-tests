package com.github.jsd.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteItemResponseDto {

    private String method;
    private String status;
    private String result;
}
