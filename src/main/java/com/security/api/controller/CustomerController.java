package com.security.api.controller;

import com.security.api.service.BusinessService;
import com.security.api.service.CustomerService;
import com.security.api.util.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @GetMapping("/business/{businessId}")
    public ResponseEntity<GeneralResponse> findByBusinessId(@PathVariable Integer businessId) {
        log.info("CustomerController.findByBusinessId");
        return ResponseEntity.ok(service.findByBusiness(businessId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<GeneralResponse> findByUserId(@PathVariable Integer userId) {
        log.info("CustomerController.findByUserId");
        return ResponseEntity.ok(service.findByUser(userId));
    }
}
