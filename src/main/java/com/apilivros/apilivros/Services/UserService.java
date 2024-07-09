package com.apilivros.apilivros.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apilivros.apilivros.Domain.User;
import com.apilivros.apilivros.Repository.UserRepository;
import com.apilivros.apilivros.Services.Exceptions.ResourceNotFoundException;

@Service
public class UserService {
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

    public User update(User obj){
        Optional<User> newObj = userRepository.findById(obj.getId());
        User user = newObj.get();
        updateUser(user, obj);
        return userRepository.save(user);
    }

    public void updateUser(User user, User obj){
        user.setName(obj.getName());
    }

}