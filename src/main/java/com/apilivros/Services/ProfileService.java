package com.apilivros.Services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apilivros.Domain.Profile;
import com.apilivros.Dto.ProfileDTO;
import com.apilivros.Dto.Mappers.BookMapper;
import com.apilivros.Dto.Mappers.UserMapper;
import com.apilivros.Repository.ProfileRepository;
import com.apilivros.Services.Exceptions.ResourceNotFoundException;

@Service
public class ProfileService {
    
    @Autowired
    private ProfileRepository profileRepository;

    public Page<ProfileDTO> findAll(Pageable pageable){
        Page<Profile> profiles = profileRepository.findAll(pageable);
        return profiles.map(this::convertToDTO);
    }

    public ProfileDTO findById(Integer id){
        Profile profile = profileRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado."));
        return convertToDTO(profile);
    }

    @Transactional
    public ProfileDTO insert(ProfileDTO dto) {
        Profile profile = fromDTO(dto);
        profile = profileRepository.save(profile);
        return convertToDTO(profile);
    }

    @Transactional
    public void delete(Integer id) {
        findById(id);
        profileRepository.deleteById(id);
    }

    @Transactional
    public ProfileDTO update(Integer id, ProfileDTO dto) {
        Profile profile = profileRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado."));
        updateProfile(profile, dto);
        profile = profileRepository.save(profile);
        return convertToDTO(profile);
    }

    public Profile findByProfilename(String profileName) {
        return profileRepository.findByUsername(profileName);
    }

    public Profile fromDTO(ProfileDTO dto) {
        Profile profile = new Profile();
        profile.setUser(UserMapper.fromDTO(dto.getUser()));
        profile.setProfileName(dto.getProfileName());
        profile.setBiography(dto.getBiography());
        profile.setUrlIcon(dto.getUrlIcon());
        profile.setFollowers(dto.getFollowers().stream().map(UserMapper::fromDTO).collect(Collectors.toList()));
        profile.setFollowing(dto.getFollowing().stream().map(UserMapper::fromDTO).collect(Collectors.toList()));
        profile.setFavoriteBooks(dto.getFavoriteBooks().stream().map(BookMapper::fromDTO).collect(Collectors.toList()));
        profile.setWishList(dto.getWishList().stream().map(BookMapper::fromDTO).collect(Collectors.toList()));
        return profile;
    }
    
    private ProfileDTO convertToDTO(Profile profile) {
        return new ProfileDTO(profile);
    }
    private void updateProfile(Profile profile, ProfileDTO dto) {
        profile.setProfileName(dto.getProfileName());
        profile.setUrlIcon(dto.getUrlIcon());
        profile.setBiography(dto.getBiography());
    }
}
