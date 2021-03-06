package com.vayusense.icconsumer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateDto {

    @JsonProperty("batchId")
    private String batchId;
    @JsonProperty("fermenterVolInL")
    private Integer fermenterVolInL;
    @JsonProperty("batchStartDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime batchStartDate;
    @JsonProperty("fermenterName")
    private String fermenterName;
    @JsonProperty("batchAgeInMin")
    private Integer batchAgeInMin;
    @JsonProperty("batchSerialNumber")
    private Integer batchSerialNumber;
    @JsonProperty("monitored")
    private MonitoredDto monitored;
    private ControllerDto controller;


}