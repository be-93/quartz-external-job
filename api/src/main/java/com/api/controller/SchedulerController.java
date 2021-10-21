package com.api.controller;

import com.external.repository.ExternalTestRepository;
import com.external.service.ExternalService;
import com.internal.repository.InternalTestRepository;
import com.internal.service.InternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor
public class SchedulerController {

    final private ExternalService externalService;
    final private InternalService internalService;

    @GetMapping(value = "/jobs", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllJob() {
        externalService.findAllExternalTest();
        internalService.findAllInternalTest();
        return "str";
    }

}
