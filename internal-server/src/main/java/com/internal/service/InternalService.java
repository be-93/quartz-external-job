package com.internal.service;

import com.internal.entity.InternalTest;
import com.internal.repository.querydsl.QueryDslInternalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalService {

    final private QueryDslInternalRepository customRepository;

    public List<InternalTest> findAllInternalTest() {
        return customRepository.findAllInternalTest();
    }

}
