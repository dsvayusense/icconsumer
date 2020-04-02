package com.vayusense.icconsumer.persistence.teva;

import com.vayusense.icconsumer.entities.MachineLearningLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository  extends ReactiveMongoRepository<MachineLearningLog,String> {
}
