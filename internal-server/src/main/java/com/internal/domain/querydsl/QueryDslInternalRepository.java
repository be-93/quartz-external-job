package com.internal.domain.querydsl;

import com.internal.domain.InternalTest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryDslInternalRepository {
    List<InternalTest> findAllInternalTest();
}
