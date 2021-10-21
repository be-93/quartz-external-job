package com.internal.service;

import com.internal.entity.InternalTest;
import com.internal.repository.querydsl.QueryDslCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalService {

    final private QueryDslCustomRepository customRepository;

    public List<InternalTest> findAllInternalTest() {
        return customRepository.findAllInternalTest();
    }

}
