package com.equipo3.explorer.controller;

import com.equipo3.explorer.model.Role;
import com.equipo3.explorer.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("roles")
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

    @PutMapping("/update")
    public ResponseEntity<?> updateRole(@RequestBody Role role){
        Optional<Role> roleOptional = roleService.updateRole(role);
        if(roleOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRole(@PathVariable Long id){
        Optional<Role> roleOptional = roleService.getRoleById(id);
        if(roleOptional.isPresent()){
            roleService.deleteRoleById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
