package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Policy;

import java.util.List;

public interface IPolicyService {

    List<Policy> getAllPolicies();
    Policy getPolicy(Long id);
    Policy savePolicy(Policy policy);
    Policy updatePolicy(Long id, Policy policy);
    void deletePolicy(Long id);
}
