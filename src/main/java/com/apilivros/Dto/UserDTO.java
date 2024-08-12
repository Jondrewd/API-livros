package com.apilivros.Dto;

import com.apilivros.apilivros.Domain.User;

import jakarta.persistence.Id;

public class UserDTO {
    @Id
    private Integer id;
    private String name;

    
    public UserDTO(User user) {
        id = user.getId();
        name = user.getName();
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
}
