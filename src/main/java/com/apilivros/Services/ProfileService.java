package com.apilivros.Services;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apilivros.Domain.Books;
import com.apilivros.Domain.Profile;
import com.apilivros.Dto.FollowerDTO;
import com.apilivros.Dto.Mappers.FollowerMapper;
import com.apilivros.Dto.Mappers.ProfileMapper;
import com.apilivros.Dto.ProfileDTO;
import com.apilivros.Dto.ProfileEditDTO;
import com.apilivros.Repository.BooksRepository;
import com.apilivros.Repository.ProfileRepository;
import com.apilivros.Services.Exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BooksRepository bookRepository;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private FollowerMapper followerMapper;

    @Transactional(readOnly = true)
public Page<ProfileDTO> findAll(Pageable pageable) {
    Page<Profile> profilesPage = profileRepository.findAll(pageable);
    List<Profile> profiles = profilesPage.getContent();

    Set<Long> allRelatedIds = profiles.stream()
        .flatMap(profile -> Stream.concat(profile.getFollowersIds().stream(), profile.getFollowingIds().stream()))
        .collect(Collectors.toSet());

    Map<Long, Profile> relatedProfilesMap = profileRepository.findAllById(allRelatedIds).stream()
        .collect(Collectors.toMap(Profile::getId, Function.identity()));

    List<ProfileDTO> profileDTOs = profiles.stream().map(profile -> {
        List<FollowerDTO> followers = profile.getFollowersIds().stream()
            .map(id -> relatedProfilesMap.get(id)) 
            .filter(Objects::nonNull)
            .map(followerMapper::toDTO)
            .collect(Collectors.toList());

        List<FollowerDTO> following = profile.getFollowingIds().stream()
            .map(id -> relatedProfilesMap.get(id)) 
            .filter(Objects::nonNull) 
            .map(followerMapper::toDTO)
            .collect(Collectors.toList());

        return profileMapper.toDTO(profile, followers, following);
    }).collect(Collectors.toList());

    return new PageImpl<>(profileDTOs, pageable, profilesPage.getTotalElements());
}


    public ProfileDTO findById(Long id) {
        return mapToDTOWithFollowers(getProfile(id));
    }

    @Transactional
    public ProfileDTO insert(ProfileDTO dto) {
        Profile profile = profileMapper.fromDTO(dto);
        return mapToDTOWithFollowers(profileRepository.save(profile));
    }

    public ProfileDTO findByusername(String username) {
        Profile profile = profileRepository.findByUsername(username);
        if (profile == null) throw new ResourceNotFoundException("Perfil não encontrado por username.");
        return mapToDTOWithFollowers(profile);
    }

    @Transactional
    public void delete(Long id) {
        profileRepository.delete(getProfile(id));
    }

    @Transactional
    public void follow(Long idFollower, Long idProfile) {
        if (idFollower.equals(idProfile)) {
            throw new IllegalArgumentException("Um perfil não pode seguir a si mesmo.");
        }

        Profile follower = getProfile(idFollower);
        Profile profileToFollow = getProfile(idProfile);

        if (!follower.getFollowingIds().add(profileToFollow.getId())) {
            throw new IllegalStateException("Esse perfil já está sendo seguido.");
        }

        profileToFollow.getFollowersIds().add(follower.getId());

        profileRepository.save(follower);
        profileRepository.save(profileToFollow);
    }

    @Transactional
    public void unfollow(Long idFollower, Long idProfile) {
        if (idFollower.equals(idProfile)) {
            throw new IllegalArgumentException("Um perfil não pode deixar de seguir a si mesmo.");
        }

        Profile follower = getProfile(idFollower);
        Profile profileToUnfollow = getProfile(idProfile);

        if (!follower.getFollowingIds().remove(profileToUnfollow.getId())) {
            throw new IllegalStateException("O perfil não está seguindo o outro perfil.");
        }

        profileToUnfollow.getFollowersIds().remove(follower.getId());

        profileRepository.save(follower);
        profileRepository.save(profileToUnfollow);
    }

    @Transactional
    public void addToWishlist(Long profileId, Long bookId) {
        Profile profile = getProfile(profileId);
        Books book = getBook(bookId);
    
        if (profile.getWishList().contains(book)) {
            throw new IllegalStateException("O livro já está na wishlist.");
        }
    
        profile.getWishList().add(book);
        
        profileRepository.save(profile);
    }
    

    @Transactional
    public void removeFromWishlist(Long profileId, Long bookId) {
        Profile profile = getProfile(profileId);
        Books book = getBook(bookId);

        if (!profile.getWishList().remove(book)) {
            throw new IllegalStateException("O livro não está na wishlist.");
        }

        profileRepository.save(profile);
    }

    @Transactional
    public void addToFavorites(Long profileId, Long bookId) {
        Profile profile = getProfile(profileId);
        Books book = getBook(bookId);

        if (profile.getFavoriteBooks().contains(book)) {
            throw new IllegalStateException("O livro já está na lista de favoritos.");
        }

        profile.getFavoriteBooks().add(book);
        
        profileRepository.save(profile);
    }


    @Transactional
    public void removeFromFavorites(Long profileId, Long bookId) {
        Profile profile = getProfile(profileId);
        Books book = getBook(bookId);

        if (!profile.getFavoriteBooks().remove(book)) {
            throw new IllegalStateException("O livro não está na lista de favoritos.");
        }

        profileRepository.save(profile);
    }

    @Transactional
    public ProfileDTO update(Long id, ProfileEditDTO dto) {
        Profile profile = getProfile(id);
        updateProfile(profile, dto);
        return mapToDTOWithFollowers(profileRepository.save(profile));
    }



    private Profile getProfile(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException("Perfil não encontrado."));
    }

    private Books getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado."));
    }

    private ProfileDTO mapToDTOWithFollowers(Profile profile) {
        List<FollowerDTO> followers = profile.getFollowersIds().stream()
            .map(id -> followerMapper.toDTO(getProfile(id)))
            .collect(Collectors.toList());

        List<FollowerDTO> following = profile.getFollowingIds().stream()
            .map(id -> followerMapper.toDTO(getProfile(id)))
            .collect(Collectors.toList());

        return profileMapper.toDTO(profile, followers, following);
    }

    private void updateProfile(Profile profile, ProfileEditDTO dto) {
        profile.setUsername(dto.getUsername());
        profile.setUrlIcon(dto.getUrlIcon());
        profile.setBiography(dto.getBiography());
    }

}
