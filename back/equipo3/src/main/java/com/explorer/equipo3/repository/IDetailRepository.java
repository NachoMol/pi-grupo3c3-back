package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetailRepository extends JpaRepository<Detail,Long> {
}
