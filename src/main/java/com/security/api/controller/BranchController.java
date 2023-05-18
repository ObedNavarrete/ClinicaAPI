package com.security.api.controller;

import com.security.api.dto.BranchDTO;
import com.security.api.service.BranchService;
import com.security.api.util.GeneralResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService service;

    @GetMapping("/findByBusiness/{idBusiness}")
    public ResponseEntity<GeneralResponse> findByBusiness(@PathVariable Integer idBusiness){
        return ResponseEntity.ok(service.findByBusiness(idBusiness));
    }

    @PostMapping("/save")
    public ResponseEntity<GeneralResponse> save(@RequestBody BranchDTO form, @RequestParam Integer idBusiness){
        return ResponseEntity.ok(service.save(form, idBusiness));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GeneralResponse> update(@RequestBody BranchDTO form, @PathVariable Integer id){
        return ResponseEntity.ok(service.update(form, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GeneralResponse> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }
}
