package com.vayusense.icconsumer.persistence.teva;

import com.vayusense.icconsumer.entities.Unit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository  extends ReactiveMongoRepository<Unit,String> {
}
