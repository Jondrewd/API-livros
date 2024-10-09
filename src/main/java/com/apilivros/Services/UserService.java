package com.apilivros.Services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apilivros.Domain.User;
import com.apilivros.Dto.UserDTO;
import com.apilivros.Dto.Mappers.ReviewMapper;
import com.apilivros.Repository.UserRepository;
import com.apilivros.Services.Exceptions.ResourceNotFoundException;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    public Page<UserDTO> findAll(Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);
        return users.map(this::convertToDTO);
    }

    public UserDTO findById(Integer id){
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado."));
        return convertToDTO(user);
    }

    public User insert(User obj) {
        return userRepository.save(obj);
    }

    public void delete(Integer id) {
        findById(id);
        userRepository.deleteById(id);
    }

    public User update(Integer id, User obj) {
        Optional<User> newObj = userRepository.findById(id);
        User user = newObj.get();
        updateUser(user, obj);
        return userRepository.save(user);
    }

    public void updateUser(User user, User obj) {
        user.setUsername(obj.getUsername());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Não foi possível achar esse nome.");
        }
    }

    public User fromDTO(UserDTO dto) {
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
    
    private UserDTO convertToDTO(User user) {
        return new UserDTO(user);
    }
}