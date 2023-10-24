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
    public ResponseEntity<String> getRoleById(@PathVariable Long id){
        Optional<Role> roleSearch = roleService.getRoleById(id);
        if(roleSearch.isPresent()){
            return ResponseEntity.ok("This role is: " + roleSearch.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role NOT_FOUND");
    }

    @PostMapping("/create")
    public ResponseEntity<Role> addRole(@RequestBody Role role){
        return ResponseEntity.ok(roleService.saveRole(role));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateRole(@RequestBody Role role){
        ResponseEntity<String> response;
        if(roleService.getRoleById(role.getId()).isPresent()){
            roleService.updateRole(role);
            response = ResponseEntity.ok("Role updated");
        }else{
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role NOT_FOUND");
        }
        return response;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRole(@PathVariable Long id){
        roleService.deleteRoleById(id);
        return ResponseEntity.ok("Role deleted successfully");
    }
}
