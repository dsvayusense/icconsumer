package com.vayusense.icconsumer.service;

import com.vayusense.icconsumer.dto.StateDto;
import com.vayusense.icconsumer.entities.MachineLearningLog;
import com.vayusense.icconsumer.entities.State;
import com.vayusense.icconsumer.entities.Unit;
import com.vayusense.icconsumer.persistence.teva.LogRepository;
import com.vayusense.icconsumer.persistence.teva.StateRepository;
import com.vayusense.icconsumer.persistence.teva.UnitRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@AllArgsConstructor
public class TevaService {

    private final StateRepository stateRepository;
    private final LogRepository logRepository;
    private final UnitRepository unitRepository;

    public Mono<String> createState(StateDto stateDto){
        ModelMapper modelMapper = new ModelMapper();
        State state = modelMapper.map(stateDto, State.class);
        state.setId(state.getBatchId()+state.getBatchAgeInMin());
        return stateRepository.save(state).doOnError(e -> {
            log.error("find an error in teva state consumer ", e);
        }).flatMap(stateM -> Mono.just(stateM.toString())).log();
    }

    public Mono<String> createMLLog(MachineLearningLog MLlog){
        return logRepository.save(MLlog).doOnError(e -> {
            log.error("find an error in teva machineLearninglog consumer ",e);
        }).flatMap(logM -> Mono.just(logM.toString())).log();
    }

    public Mono<String> createUnit(Unit unit){
        return unitRepository.save(unit).doOnError(e -> {
            log.error("find an error in teva unit consumer ",e);
        }).flatMap(unitM -> Mono.just(unitM.toString())).log();

    }


}
