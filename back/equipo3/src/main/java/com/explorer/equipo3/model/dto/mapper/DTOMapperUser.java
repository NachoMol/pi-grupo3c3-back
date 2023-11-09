package com.explorer.equipo3.model.dto.mapper;

import com.explorer.equipo3.model.User;
import com.explorer.equipo3.model.dto.UserDTO;

public class DTOMapperUser {

    private User user;

    private DTOMapperUser(){

    }

    public static DTOMapperUser builder(){
        return new DTOMapperUser();
    }

    public DTOMapperUser setUser(User user) {
        this.user = user;
        return this;
    }

    public UserDTO build(){
        if(user == null){
            throw new RuntimeException("Incorrect Params");
        }
        return new UserDTO(this.user.getId(), this.user.getEmail());
    }
}
