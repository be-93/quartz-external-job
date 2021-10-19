package com.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

    @GetMapping(value = "/jobs", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllJob() {
        return "test";
    }

}
