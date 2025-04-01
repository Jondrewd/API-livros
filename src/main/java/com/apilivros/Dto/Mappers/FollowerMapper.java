package com.apilivros.Dto.Mappers;

import org.springframework.stereotype.Component;

import com.apilivros.Dto.FollowerDTO;
import com.apilivros.Domain.Profile;

@Component
public class FollowerMapper {

    public FollowerDTO toDTO(Profile profile) {
        FollowerDTO dto = new FollowerDTO();
        dto.setId(profile.getId());
        dto.setUsername(profile.getUsername());
        dto.setUrlIcon(profile.getUrlIcon());
        return dto;
    }
}
