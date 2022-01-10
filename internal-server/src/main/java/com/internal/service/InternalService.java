package com.internal.service;

import com.internal.domain.InternalTest;
import com.internal.domain.querydsl.QueryDslInternalRepository;
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
