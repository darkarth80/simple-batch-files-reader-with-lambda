package com.darkarth.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.darkarth.demo.util.enumeration.Type;

import lombok.Data;

@Data
public class SimpleDTO {

    @JsonProperty()
    private String id;

    @JsonProperty()
    private String name;

    @JsonProperty()
    private String date;

    @JsonProperty()
    private Type type;

}