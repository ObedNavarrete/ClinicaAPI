package com.security.api.controller;

import com.security.api.dto.ProfileDTO;
import com.security.api.service.ProfileService;
import com.security.api.util.GeneralResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/save")
    public ResponseEntity<GeneralResponse> save(@RequestBody @Valid ProfileDTO form){
        return ResponseEntity.ok(this.profileService.save(form));
    }

    @GetMapping("/getByBusiness/{idBusiness}")
    public ResponseEntity<GeneralResponse> getByBusiness(@PathVariable Integer idBusiness){
        return ResponseEntity.ok(this.profileService.getByBusiness(idBusiness));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<GeneralResponse> getById(@PathVariable Integer id, @RequestParam Integer idBusiness){
        return ResponseEntity.ok(this.profileService.getById(id, idBusiness));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GeneralResponse> delete(@PathVariable Integer id, @RequestParam Integer idBusiness){
        return ResponseEntity.status(401).body(this.profileService.delete(id, idBusiness));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GeneralResponse> update(@PathVariable Integer id, @RequestBody @Valid ProfileDTO form){
        return ResponseEntity.ok(this.profileService.update(form, id));
    }
}
