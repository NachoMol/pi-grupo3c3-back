package com.equipo3.explorer.service;

import com.equipo3.explorer.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    List<Role> getAllRoles();
    Optional<Role> getRoleById(Long id);
    Role saveRole(Role role);
    Role updateRole(Role role);
    void deleteRoleById(Long id);
}

