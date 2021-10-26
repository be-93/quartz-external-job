package com.api.controller;

import com.external.service.ExternalService;
import com.internal.service.InternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor
public class SchedulerController {

    final private InternalService internalService;
    final private ExternalService externalService;

    @GetMapping(value = "/jobs", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllJob() {
        internalService.findAllInternalTest();
        externalService.findAllExternalTest();
        return "str";
    }

}
