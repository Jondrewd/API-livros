package com.apilivros.Services;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apilivros.Domain.User;
import com.apilivros.Repository.UserRepository;
import com.apilivros.Services.Exceptions.ResourceNotFoundException;



@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();    
    }

    public User findById(Integer id){
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado."));
    }


    public User insert(User obj){
        return userRepository.save(obj);
    }

    public void delete(Integer id){
        findById(id);
        userRepository.deleteById(id);
    }

    public User update(Integer id, User obj){
        Optional<User> newObj = userRepository.findById(id);
        User user = newObj.get();
        updateUser(user, obj);
        return userRepository.save(user);
    }

    public void updateUser(User user, User obj){
        user.setUsername(obj.getUsername());
    }

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        }else{
            throw new UsernameNotFoundException("Não foi possível achar esse nome.");
        }
    }

}