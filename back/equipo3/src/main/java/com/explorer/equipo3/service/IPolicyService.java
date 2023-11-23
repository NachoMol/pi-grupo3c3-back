package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Policy;

import java.util.List;
import java.util.Optional;

public interface IPolicyService {

    List<Policy> getAllPolicies();
    Optional<Policy> getPolicy(Long id);
    Policy savePolicy(Policy policy);
    Optional<Policy> updatePolicy(Long id, Policy policy);
    void deletePolicy(Long id);
}
