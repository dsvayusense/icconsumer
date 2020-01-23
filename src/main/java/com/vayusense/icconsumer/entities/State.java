package com.vayusense.icconsumer.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "stateaction" )
@Data
public class State {

    @Id
    private String id;
    private Integer co2;
    private Integer ph;
    private LocalDate startime;
    private LocalDate endtime;
}