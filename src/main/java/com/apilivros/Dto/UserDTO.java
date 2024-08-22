package com.apilivros.Dto;

import com.apilivros.Domain.User;

import jakarta.persistence.Id;

public class UserDTO {
    @Id
    private Integer id;
    private String username;

    
    public UserDTO(User user) {
        id = user.getId();
        username = user.getUsername();
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    
}
