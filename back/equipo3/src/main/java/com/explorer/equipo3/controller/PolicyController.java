package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.Policy;
import com.explorer.equipo3.service.IPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/policies")
public class PolicyController {

    @Autowired
    private IPolicyService policyService;

    @GetMapping()
    public ResponseEntity<List<Policy>> getAllPolicies(){
        return ResponseEntity.ok(policyService.getAllPolicies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPolicy(@PathVariable Long id){
        return ResponseEntity.ok(policyService.getPolicy(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPolicy(@RequestBody Policy policy){
        return ResponseEntity.ok(policyService.savePolicy(policy));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePolicy(@PathVariable Long id, @RequestBody Policy policy) {
        Optional<Policy> policyOptional = policyService.updatePolicy(id, policy);
        if (policyOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePolicy(Long id){
        Optional<Policy> policyOptional = policyService.getPolicy(id);
        if(policyOptional.isPresent()){
            policyService.deletePolicy(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
