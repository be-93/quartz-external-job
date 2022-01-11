package com.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor
public class SchedulerController {

    @GetMapping(value = "/jobs", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllJob() {
        return "str";
    }

}
