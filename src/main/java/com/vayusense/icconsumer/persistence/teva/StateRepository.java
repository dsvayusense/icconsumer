package com.vayusense.icconsumer.persistence.teva;

import com.vayusense.icconsumer.entities.State;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends ReactiveMongoRepository<State,String> {
}
