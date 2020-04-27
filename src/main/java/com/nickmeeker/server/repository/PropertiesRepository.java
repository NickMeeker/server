package com.nickmeeker.server.repository;

import com.nickmeeker.server.document.Property;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PropertiesRepository extends MongoRepository<Property, String> {
}
