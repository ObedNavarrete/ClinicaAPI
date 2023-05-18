package com.security.api.controller;

import com.security.api.dto.ResourceDTO;
import com.security.api.service.ResourceService;
import com.security.api.util.GeneralResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService service;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GeneralResponse> save(@RequestBody @Valid ResourceDTO form){
        return ResponseEntity.ok(service.save(form));
    }

    @GetMapping("/getAll")
    public ResponseEntity<GeneralResponse> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GeneralResponse> update(@RequestBody @Valid ResourceDTO form, @RequestParam Integer id){
        return ResponseEntity.ok(service.update(id, form));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GeneralResponse> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }
}
