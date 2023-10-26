package com.equipo3.explorer.repository;

import com.equipo3.explorer.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IModelRepository extends JpaRepository<Model,Long> {
}
