package com.apilivros.apilivros.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apilivros.apilivros.Domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    
}
