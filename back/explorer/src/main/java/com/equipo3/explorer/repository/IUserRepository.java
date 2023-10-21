package com.equipo3.explorer.repository;

import com.equipo3.explorer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
}
