package com.core.repository.custom;

import com.core.entity.CoreTest;

import java.util.List;

public interface QueryDslCustomRepository {
    List<CoreTest> findAllCoreTest();
}
