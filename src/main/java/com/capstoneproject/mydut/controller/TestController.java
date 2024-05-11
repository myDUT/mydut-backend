package com.capstoneproject.mydut.controller;

import com.capstoneproject.mydut.common.enums.EnrollmentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vndat00
 * @since 5/9/2024
 */

@RestController
@RequiredArgsConstructor
@RequestMapping
public class TestController {
    @GetMapping("/test")
    public String testController() {
        return "Server run OK!";
    }
}
