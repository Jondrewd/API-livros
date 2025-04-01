package com.apilivros.Dto.Mappers;

import java.util.stream.Collectors;

import com.apilivros.Domain.User;
import com.apilivros.Dto.UserDTO;

public class UserMapper {
    public static User fromDTO(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId()); 
        user.setUsername(dto.getUsername());
        user.setFullName(dto.getFullName());
        user.setReviews(dto.getReviews()
                            .stream()
                            .map(ReviewMapper::fromDTO)
                            .collect(Collectors.toList()));
        return user;
    }
    public static UserDTO toDTO(User user){
        return new UserDTO(user);
    }
}
