package com.vayusense.icconsumer.entities;

import com.vayusense.icconsumer.dto.ControllerDto;
import com.vayusense.icconsumer.dto.MonitoredDto;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "state" )
@Data
public class State {

    @Id
    private String id;
    private String batchId;
    private Integer fermenterVolInL;
    private LocalDateTime batchStartDate;
    private String fermenterName;
    private Integer batchAgeInMin;
    private Integer batchSerialNumber;
    private MonitoredDto monitored;
    private ControllerDto controller;

}