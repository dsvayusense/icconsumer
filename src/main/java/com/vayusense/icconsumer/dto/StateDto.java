package com.vayusense.icconsumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StateDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("co2")
    private Integer co2;
    @JsonProperty("ph")
    private Integer ph;

    @Override
    public String toString() {
        return "StateDto {id=" + id + ",co2=" + co2 + ", ph=" + ph + "}";
    }

}
