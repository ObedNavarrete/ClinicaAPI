package com.security.api.controller;

import com.security.api.dto.BusinessDTO;
import com.security.api.service.BusinessService;
import com.security.api.util.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<GeneralResponse> findAll(){
        log.info("BusinessController.findAll");
        return ResponseEntity.ok(service.findAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<GeneralResponse> save(@RequestBody @Valid BusinessDTO request){
        log.info("BusinessController.save");
        return ResponseEntity.ok(service.save(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GeneralResponse> update(@RequestBody @Valid BusinessDTO request, @PathVariable Integer id){
        log.info("BusinessController.update");
        return ResponseEntity.ok(service.update(request, id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GeneralResponse> delete(@PathVariable Integer id){
        log.info("BusinessController.delete");
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<GeneralResponse> findById(@PathVariable Integer id){
        log.info("BusinessController.findById");
        return ResponseEntity.ok(service.findById(id));
    }
}
