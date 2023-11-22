package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPolicyRepository extends JpaRepository<Policy, Long> {
}
