package com.apilivros.Dto.Mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.apilivros.Domain.Profile;
import com.apilivros.Dto.FollowerDTO;
import com.apilivros.Dto.ProfileDTO;

@Component
public class ProfileMapper {

    public Profile fromDTO(ProfileDTO dto) {
        Profile profile = new Profile();
        profile.setUser(UserMapper.fromDTO(dto.getUser()));
        profile.setUsername(dto.getUsername());
        profile.setBiography(dto.getBiography());
        profile.setUrlIcon(dto.getUrlIcon());

        profile.setFollowersIds(dto.getFollowers().stream()
            .map(FollowerDTO::getId)
            .collect(Collectors.toList()));

        profile.setFollowingIds(dto.getFollowing().stream()
            .map(FollowerDTO::getId)
            .collect(Collectors.toList()));

        profile.setFavoriteBooks(dto.getFavoriteBooks().stream()
            .map(BookMapper::fromDTO)
            .collect(Collectors.toList()));

        profile.setWishList(dto.getWishList().stream()
            .map(BookMapper::fromDTO)
            .collect(Collectors.toList()));

        return profile;
    }

    public ProfileDTO toDTO(Profile profile, List<FollowerDTO> followers, List<FollowerDTO> following) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(profile.getId());
        dto.setUser(UserMapper.toDTO(profile.getUser()));
        dto.setUsername(profile.getUsername());
        dto.setBiography(profile.getBiography());
        dto.setUrlIcon(profile.getUrlIcon());

        dto.setFollowers(followers);
        dto.setFollowing(following);

        dto.setFavoriteBooks(profile.getFavoriteBooks().stream()
            .map(BookMapper::convertBookToDTO)
            .collect(Collectors.toList()));

        dto.setWishList(profile.getWishList().stream()
            .map(BookMapper::convertBookToDTO)
            .collect(Collectors.toList()));

        return dto;
    }
}
