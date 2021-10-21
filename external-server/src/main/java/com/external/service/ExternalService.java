package com.external.service;

import com.external.entity.ExternalTest;
import com.external.repository.querydsl.QueryDslCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalService {

    final private QueryDslCustomRepository customRepository;

    public List<ExternalTest> findAllExternalTest() {
        return customRepository.findAllExternalTest();
    }

}
