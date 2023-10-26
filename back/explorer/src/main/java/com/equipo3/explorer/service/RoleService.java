package com.equipo3.explorer.service;

import com.equipo3.explorer.model.Role;
import com.equipo3.explorer.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService{

    @Autowired
    private IRoleRepository roleRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    @Transactional
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public Optional<Role> updateRole(Long id, Role role) {
        Optional<Role> roleExist = roleRepository.findById(id);
        Role roleOptional = null;
        if(roleExist.isPresent()){
            Role roleDB = roleExist.orElseThrow();
            roleDB.setName(role.getName());
            roleOptional = roleRepository.save(roleDB);
        }
        return Optional.ofNullable(roleOptional);
    }

    @Override
    @Transactional
    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }
}
