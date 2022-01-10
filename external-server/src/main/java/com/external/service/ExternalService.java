package com.external.service;

import com.external.domain.ExternalTest;
import com.external.domain.querydsl.QueryDslExternalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalService {

    final private QueryDslExternalRepository customRepository;

    public List<ExternalTest> findAllExternalTest() {
        return customRepository.findAllExternalTest();
    }

}
