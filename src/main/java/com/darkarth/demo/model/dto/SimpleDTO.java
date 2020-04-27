package com.darkarth.demo.model.dto;

import com.darkarth.demo.util.enumeration.Type;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
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