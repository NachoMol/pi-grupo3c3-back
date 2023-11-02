package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    List<Role> getAllRoles();
    Optional<Role> getRoleById(Long id);
    Role saveRole(Role role);
    Optional<Role> updateRole(Long id, Role role);
    void deleteRoleById(Long id);
}
