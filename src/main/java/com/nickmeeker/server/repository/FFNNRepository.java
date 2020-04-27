package com.nickmeeker.server.repository;

import com.nickmeeker.server.document.FFNN;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FFNNRepository extends MongoRepository<FFNN, Integer> {
}
