package com.internal.repository.querydsl;

import com.internal.entity.InternalTest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryDslInternalRepository {
    List<InternalTest> findAllInternalTest();
}
