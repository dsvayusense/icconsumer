package com.vayusense.icconsumer.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Document(collection = "unit")
@NoArgsConstructor
@Data
public class Unit {

    @Id
    private String id;
    private String fermenterVolInL;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime batchStartDate;
    private String temperature;
    private String pressure;
    private String airFlow;
    private String fs;
    private String fa;
    private String agitation;
    @Field("do")
    private String dissolvedOxygen;
    private String co2;
    private String mass;
    private String power;
    private String incyte;
    private String amnConcent;
    private String tobraConcent;
    private String kanamConcent;
    private String dextroseConcent;
    private String pcv;
}
