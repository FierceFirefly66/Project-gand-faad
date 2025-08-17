package com.MangoDesk.MangoDesk.Repositories;

import com.MangoDesk.MangoDesk.Model.Summary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SummaryRepository extends MongoRepository<Summary, String> {
}
