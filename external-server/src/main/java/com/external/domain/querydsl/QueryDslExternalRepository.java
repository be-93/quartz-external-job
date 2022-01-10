package com.external.domain.querydsl;

import com.external.domain.ExternalTest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryDslExternalRepository {
    List<ExternalTest> findAllExternalTest();
}
