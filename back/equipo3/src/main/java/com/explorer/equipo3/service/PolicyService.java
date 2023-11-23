package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Policy;
import com.explorer.equipo3.repository.IPolicyRepository;
import com.explorer.equipo3.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyService implements IPolicyService{

    @Autowired
    private IPolicyRepository policyRepository;
    @Override
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    @Override
    public Optional<Policy> getPolicy(Long id) {
        return policyRepository.findById(id);
    }

    @Override
    public Policy savePolicy(Policy policy) {
        return policyRepository.save(policy);
    }

    @Override
    public Optional<Policy> updatePolicy(Long id, Policy policy) {

        Optional<Policy> policyOptional = policyRepository.findById(id);
        Policy policyResponse = null;
        if(policyOptional.isPresent()){
            Policy policyDB = policyOptional.orElseThrow();
            policyDB.setDescription(policy.getDescription());
            policyResponse = policyRepository.save(policyDB);
        }
        return Optional.ofNullable(policyResponse);
    }

    @Override
    public void deletePolicy(Long id) {
        policyRepository.deleteById(id);
    }
}
