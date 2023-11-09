package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.Role;
import com.explorer.equipo3.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping()
    public ResponseEntity<List<Role>> getAllRoles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id){
        Optional<Role> roleSearch = roleService.getRoleById(id);
        if(roleSearch.isPresent()){
            return ResponseEntity.ok(roleSearch.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Role> addRole(@RequestBody Role role){
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.saveRole(role));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody Role role){
        Optional<Role> roleOptional = roleService.updateRole(id, role);
        if(roleOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id){
        Optional<Role> roleOptional = roleService.getRoleById(id);
        if(roleOptional.isPresent()){
            roleService.deleteRoleById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
