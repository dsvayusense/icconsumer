package com.vayusense.icconsumer.service;

import com.vayusense.icconsumer.dto.StateDto;
import com.vayusense.icconsumer.entities.State;
import com.vayusense.icconsumer.persistence.StateRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class StateService {

    private final StateRepository stateRepository;

    public Mono<StateDto> SwitchMethodByConsume(StateDto stateDto){
        String id = stateDto.getId();
        if(id == null || id.isEmpty())
            return  createState(stateDto);
        return saveState(stateDto);
    }

    public Mono<StateDto> createState(StateDto stateDto){
        ModelMapper modelMapper = new ModelMapper();
        State state = modelMapper.map(stateDto, State.class);
        stateRepository.insert(state).subscribe();
        return Mono.just(stateDto);

    }

    public Mono<StateDto> saveState(StateDto stateDto){
        ModelMapper modelMapper = new ModelMapper();
        State state = modelMapper.map(stateDto, State.class);
        return stateRepository.findById(state.getId()).doOnSuccess(findState -> {
            findState = state;
            stateRepository.save(findState).subscribe();
        }).flatMap(findState -> Mono.just(stateDto));

    }
}
