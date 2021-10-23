package com.external.repository.querydsl;

import com.external.entity.ExternalTest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryDslExternalRepository {
    List<ExternalTest> findAllExternalTest();
}
