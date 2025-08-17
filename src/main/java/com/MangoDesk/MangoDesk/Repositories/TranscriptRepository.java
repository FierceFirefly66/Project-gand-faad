package com.MangoDesk.MangoDesk.Repositories;

import com.MangoDesk.MangoDesk.Model.Transcript;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TranscriptRepository extends MongoRepository<Transcript, String> {
}

