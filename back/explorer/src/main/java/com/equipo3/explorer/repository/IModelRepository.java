package com.equipo3.explorer.repository;

import com.equipo3.explorer.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IModelRepository extends JpaRepository<Model,Long> {
}
